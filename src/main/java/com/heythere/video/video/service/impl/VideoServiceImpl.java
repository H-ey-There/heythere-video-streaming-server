package com.heythere.video.video.service.impl;

import com.heythere.video.video.dto.CommentRegisterRequestDto;
import com.heythere.video.video.dto.LargeCommentRegisterRequestDto;
import com.heythere.video.video.exception.ResourceNotFoundException;
import com.heythere.video.video.mapper.VideoResponseMapper;
import com.heythere.video.video.model.Comment;
import com.heythere.video.video.model.LargeComment;
import com.heythere.video.video.model.User;
import com.heythere.video.video.model.Video;
import com.heythere.video.video.repository.CommentRepository;
import com.heythere.video.video.repository.LargeCommentRepository;
import com.heythere.video.video.repository.UserRepository;
import com.heythere.video.video.repository.VideoRepository;
import com.heythere.video.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VideoServiceImpl implements VideoService {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final CommentRepository commentRepository;
    private final LargeCommentRepository largeCommentRepository;
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
    public VideoResponseMapper findVideoById(Long videoId) {
        return VideoResponseMapper.of(
                videoRepository.findById(videoId)
                        .orElseThrow(() -> new ResourceNotFoundException("Video", "id", videoId))
                .updateViewCount()
        );
    }

    @Override
    @Transactional
    public Long uploadVideo(final Long requestUserId,
                            final String title,
                            final String description,
                            final MultipartFile thumbnail,
                            final  MultipartFile video) throws IOException {
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

        return largeComment.addLargeCommentToComment(comment).getId();
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

    private User getUser(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",id));
    }
}
