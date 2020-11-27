package com.heythere.video.video.service.impl;

import com.heythere.video.video.dto.CommentRegisterRequestDto;
import com.heythere.video.video.dto.LargeCommentRegisterRequestDto;
import com.heythere.video.video.exception.ResourceNotFoundException;
import com.heythere.video.video.mapper.CommentResponseMapper;
import com.heythere.video.video.mapper.LargeCommentResponseMapper;
import com.heythere.video.video.mapper.VideoGoodBadStatusMapper;
import com.heythere.video.video.mapper.VideoResponseMapper;
import com.heythere.video.video.model.*;
import com.heythere.video.video.repository.*;
import com.heythere.video.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoServiceImpl implements VideoService {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final CommentRepository commentRepository;
    private final LargeCommentRepository largeCommentRepository;
    private final VideoAndUserRepository videoAndUserRepository;
    private final AmazonS3StorageService amazonS3StorageService;
    private final RestTemplate restTemplate;


    @Override
    @Transactional
    public List<VideoResponseMapper> findAllVideos(Pageable pageable) {
        return videoRepository.findAll(pageable).stream()
                .map(VideoResponseMapper::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VideoResponseMapper findVideoById(Long videoId, Long userId) {

        final User user = getUser(userId);
        final Video video = getVideo(videoId)
                .updateViewCount();
        boolean goodStatus = false;
        boolean badStatus = false;
        if (!videoAndUserRepository.existsByUserAndVideo(user, video)) {
            videoAndUserRepository.save(VideoAndUser.builder()
                    .user(user)
                    .video(video)
                    .build());
        } else {
            final VideoAndUser vu = getVu(user, video);

            goodStatus = vu.getGoodStatus();
            badStatus = vu.getBadStatus();
        }

        return VideoResponseMapper.of(video, goodStatus, badStatus);
    }

    @Override
    @Transactional
    public Long uploadVideo(final Long requestUserId,
                            final String title,
                            final String description,
                            final MultipartFile thumbnail,
                            final MultipartFile video) throws IOException {
        final User user = getUser(requestUserId);

        return videoRepository.save(Video.builder()
                .title(title)
                .description(description)
                .viewCount(0)
                .user(user)
                .thumbnailUrl(amazonS3StorageService.uploadThumbnail(thumbnail, title, "thumbnail"))
                .videoUrl(amazonS3StorageService.uploadVideo(video, title, "video"))
                .build()).getId();
    }


    @Override
    @Transactional
    public Long registerComment(final CommentRegisterRequestDto payload) {
        final Video video = videoRepository.findById(payload.getVideoId())
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", payload.getVideoId()));

        final Comment comment = commentRepository.save(Comment.builder()
                .comment(payload.getComment())
                .video(video)
                .user(getUser(payload.getRequestUserId()))
                .build());

        return comment.addCommentToVideo(video).getId();
    }

    @Override
    public Long registerLargeComment(final LargeCommentRegisterRequestDto payload) {

        final Comment comment = commentRepository.findById(payload.getCommentId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", payload.getCommentId()));

        final LargeComment largeComment = largeCommentRepository.save(LargeComment.builder()
                .comment(comment)
                .largeComment(payload.getLargeComment())
                .user(getUser(payload.getRequestUserId()))
                .build());

        return largeComment.getId();
    }

    @Override
    @Transactional
    public List<VideoResponseMapper> findVideosTopViewCountLimitTen(Pageable pageable) {
        return videoRepository.findVideosTopViewCountLimitTen(pageable)
                .stream()
                .map(VideoResponseMapper::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<VideoResponseMapper> findVideosTopGoodCountLimitTen(Pageable pageable) {
        return videoRepository.findVideosTopGoodCountLimitTen(pageable)
                .stream()
                .map(VideoResponseMapper::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CommentResponseMapper> findAllCommentByVideoId(Long videoId) {
        final Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", videoId));

        return video.getComments()
                .stream()
                .map(CommentResponseMapper::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<LargeCommentResponseMapper> findAllLargeCommentByCommentId(Long commentId) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        return comment.getLargeComments()
                .stream()
                .map(LargeCommentResponseMapper::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VideoGoodBadStatusMapper pressGoodButton(Long userId, Long videoId) {
        final User user = getUser(userId);
        final Video video = getVideo(videoId);
        final VideoAndUser vu = getVu(user, video);

        if (!vu.getGoodStatus()) {
            video.increaseGood();
        } else {
            video.decreaseGood();
        }
        vu.updateGoodStatus();

        return VideoGoodBadStatusMapper.builder()
                .good(video.getGoodCount())
                .bad(video.getBadCount())
                .goodStatus(vu.getGoodStatus())
                .badStatus(vu.getBadStatus())
                .build();
    }

    @Override
    @Transactional
    public VideoGoodBadStatusMapper pressBadButton(Long userId, Long videoId) {
        final User user = getUser(userId);
        final Video video = getVideo(videoId);
        final VideoAndUser vu = getVu(user, video);

        if (!vu.getBadStatus()) {
            video.increaseBad();
        } else {
            video.decreaseBad();
        }
        vu.updateBadStatus();

        return VideoGoodBadStatusMapper.builder()
                .good(video.getGoodCount())
                .bad(video.getBadCount())
                .goodStatus(vu.getGoodStatus())
                .badStatus(vu.getBadStatus())
                .build();
    }

    private VideoAndUser getVu(final User user, final Video video) {
        return videoAndUserRepository.findByUserAndVideo(user, video)
                .orElseThrow(() -> new ResourceNotFoundException("VideoAndUser"));
    }


    private User getUser(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    private Video getVideo(final Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", id));
    }
}
