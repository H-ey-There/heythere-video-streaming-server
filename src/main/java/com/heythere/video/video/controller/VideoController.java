package com.heythere.video.controller;

import com.heythere.video.dto.CommentRegisterRequestDto;
import com.heythere.video.dto.LargeCommentRegisterRequestDto;
import com.heythere.video.mapper.VideoResponseMapper;
import com.heythere.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class VideoController {
    private final VideoService videoService;

    @GetMapping("video")
    public ResponseEntity<List<VideoResponseMapper>> findAllVideo(final Pageable pageable) {
        return ResponseEntity.ok(videoService.findAllVideos(pageable));
    }


    @GetMapping("video/{videoId}")
    public ResponseEntity<VideoResponseMapper> findVideoById(@PathVariable("videoId") final Long videoId) {
        return ResponseEntity.ok(videoService.findVideoById(videoId));
    }

    @PostMapping("registration")
    public ResponseEntity<Long> uploadVideo(@RequestParam("requestUserId") final Long requestUserId,
                                            @RequestParam("title") final String title,
                                            @RequestParam("description") final String description,
                                            @RequestParam("thumbnail") final MultipartFile thumbnail,
                                            @RequestParam("video") final MultipartFile video) throws IOException {
        return new ResponseEntity<>(videoService.uploadVideo(requestUserId, title, description, thumbnail, video), HttpStatus.CREATED);
    }

    @PostMapping("registration/comment")
    public ResponseEntity<Long> registerComment(@RequestBody final CommentRegisterRequestDto payload) {
        return new ResponseEntity<>(videoService.registerComment(payload), HttpStatus.CREATED);
    }

    @PostMapping("registration/largeComment")
    public ResponseEntity<Long> registerLargeComment(@RequestBody final LargeCommentRegisterRequestDto payload) {
        return new ResponseEntity<>(videoService.registerLargeComment(payload), HttpStatus.CREATED);
    }
}
