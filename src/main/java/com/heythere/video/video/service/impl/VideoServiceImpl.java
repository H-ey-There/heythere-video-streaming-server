package com.heythere.video.video.service.impl;

import com.heythere.video.video.dto.CommentRegisterRequestDto;
import com.heythere.video.video.dto.LargeCommentRegisterRequestDto;
import com.heythere.video.video.dto.UploadVideoRequestDto;
import com.heythere.video.video.exception.ResourceNotFoundException;
import com.heythere.video.video.mapper.VideoResponseMapper;
import com.heythere.video.video.model.Comment;
import com.heythere.video.video.model.LargeComment;
import com.heythere.video.video.model.Video;
import com.heythere.video.video.repository.CommentRepository;
import com.heythere.video.video.repository.LargeCommentRepository;
import com.heythere.video.video.repository.UserRepository;
import com.heythere.video.video.repository.VideoRepository;
import com.heythere.video.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class VideoServiceImpl implements VideoService {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final CommentRepository commentRepository;
    private final LargeCommentRepository largeCommentRepository;

    @Override
    public VideoResponseMapper findVideoById(Long videoId) {
        return null;
    }

    @Override
    public Long uploadVideo(final UploadVideoRequestDto payload,
                            final MultipartFile thumbnail,
                            final MultipartFile video) {
        return videoRepository.save(Video.builder()
                .title(payload.getTitle())
                .description(payload.getDescription())
                .viewCount(0)
                .user(userRepository.findById(payload.getRequestUserId()).get())
                .thumbnailUrl(thumbnail.getOriginalFilename())
                .videoUrl(video.getOriginalFilename())
                .build()).getId();

        // need to compression files
    }

    @Override
    public Long registerComment(final CommentRegisterRequestDto payload) {
        final Video video = videoRepository.findById(payload.getVideoId())
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", payload.getVideoId()));

        final Comment comment = commentRepository.save(Comment.builder()
                .comment(payload.getComment())
                .video(video)
                .user(userRepository.findById(payload.getRequestUserId()).get())
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
                .user(userRepository.findById(payload.getRequestUserId()).get())
                .build());

        return largeComment.addLargeCommentToComment(comment).getId();
    }
}
