package com.heythere.video.video.service;

import com.heythere.video.video.dto.CommentRegisterRequestDto;
import com.heythere.video.video.dto.LargeCommentRegisterRequestDto;
import com.heythere.video.video.dto.UploadVideoRequestDto;
import com.heythere.video.video.mapper.VideoResponseMapper;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    VideoResponseMapper findVideoById(final Long videoId);
    Long uploadVideo( final UploadVideoRequestDto payload,
                      final MultipartFile thumbnail,
                      final MultipartFile video);

    Long registerComment(final CommentRegisterRequestDto payload);
    Long registerLargeComment(final LargeCommentRegisterRequestDto payload);
}
