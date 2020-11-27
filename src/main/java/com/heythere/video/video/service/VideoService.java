package com.heythere.video.video.service;

import com.heythere.video.video.dto.CommentRegisterRequestDto;
import com.heythere.video.video.dto.LargeCommentRegisterRequestDto;
import com.heythere.video.video.mapper.CommentResponseMapper;
import com.heythere.video.video.mapper.LargeCommentResponseMapper;
import com.heythere.video.video.mapper.VideoGoodBadStatusMapper;
import com.heythere.video.video.mapper.VideoResponseMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    List<VideoResponseMapper> findAllVideos(final Pageable pageable);
    VideoResponseMapper findVideoById(final Long videoId, final Long userId);
    Long uploadVideo( final Long requestUserId,
                      final String title,
                      final String description,
                      final MultipartFile thumbnail,
                      final MultipartFile video) throws IOException;

    Long registerComment(final CommentRegisterRequestDto payload);
    Long registerLargeComment(final LargeCommentRegisterRequestDto payload);
    List<VideoResponseMapper> findVideosTopViewCountLimitTen(final Pageable pageable);
    List<VideoResponseMapper> findVideosTopGoodCountLimitTen(final Pageable pageable);
    List<CommentResponseMapper> findAllCommentByVideoId(final Long videoId);
    List<LargeCommentResponseMapper> findAllLargeCommentByCommentId(final Long commentId);
    VideoGoodBadStatusMapper pressGoodButton(final Long userId, final Long videoId);
    VideoGoodBadStatusMapper pressBadButton(final Long userId, final Long videoId);
}

