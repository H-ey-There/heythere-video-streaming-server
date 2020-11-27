package com.heythere.video.video.controller;

import com.heythere.video.video.dto.CommentRegisterRequestDto;
import com.heythere.video.video.dto.LargeCommentRegisterRequestDto;
import com.heythere.video.video.dto.RequestUserIdDto;
import com.heythere.video.video.mapper.CommentResponseMapper;
import com.heythere.video.video.mapper.LargeCommentResponseMapper;
import com.heythere.video.video.mapper.VideoGoodBadStatusMapper;
import com.heythere.video.video.mapper.VideoResponseMapper;
import com.heythere.video.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class VideoController {
    private final VideoService videoService;

    @GetMapping("video")
    public ResponseEntity<List<VideoResponseMapper>> findAllVideo(final Pageable pageable) {
        return ResponseEntity.ok(videoService.findAllVideos(pageable));
    }


    @GetMapping("video/{videoId}")
    public ResponseEntity<VideoResponseMapper> findVideoById(@PathVariable("videoId") final Long videoId,
                                                             @RequestBody final RequestUserIdDto payload) {
        return ResponseEntity.ok(videoService.findVideoById(videoId, payload.getRequestUserId()));
    }

    @PostMapping("registration")
    public ResponseEntity<Long> uploadVideo(@RequestParam("requestUserId") final Long requestUserId,
                                            @RequestParam("title") final String title,
                                            @RequestParam("description") final String description,
                                            @RequestParam("thumbnail") final MultipartFile thumbnail,
                                            @RequestParam("video") final MultipartFile video) throws IOException {
        return new ResponseEntity<>(videoService.uploadVideo(requestUserId, title, description, thumbnail, video), HttpStatus.CREATED);
    }

    @GetMapping("video/{id}/comments")
    public List<CommentResponseMapper> findAllCommentByVideoId(@PathVariable("id") final Long videoId){
        return videoService.findAllCommentByVideoId(videoId);
    }

    @GetMapping("comment/{id}/largeComment")
    public List<LargeCommentResponseMapper> findAllLargeCommentByCommentId(@PathVariable("id") final Long commentId){
        return videoService.findAllLargeCommentByCommentId(commentId);
    }

    @PostMapping("registration/comment")
    public ResponseEntity<Long> registerComment(@RequestBody final CommentRegisterRequestDto payload) {
        return new ResponseEntity<>(videoService.registerComment(payload), HttpStatus.CREATED);
    }

    @PostMapping("registration/largeComment")
    public ResponseEntity<Long> registerLargeComment(@RequestBody final LargeCommentRegisterRequestDto payload) {
        return new ResponseEntity<>(videoService.registerLargeComment(payload), HttpStatus.CREATED);
    }

    @GetMapping("order/topView")
    public ResponseEntity<List<VideoResponseMapper>> findVideosTopViewCountLimitTen(Pageable pageable) {
        return ResponseEntity.ok(videoService.findVideosTopViewCountLimitTen(pageable));
    }

    @GetMapping("order/goodCount")
    public ResponseEntity<List<VideoResponseMapper>> findVideosTopGoodCountLimitTen(Pageable pageable) {
        return ResponseEntity.ok(videoService.findVideosTopGoodCountLimitTen(pageable));
    }

    @GetMapping("video/{videoId}/good/user/{userId}")
    public VideoGoodBadStatusMapper pressGood(@PathVariable("userId") final Long userId,
                                              @PathVariable("videoId") final Long videoId) {
        return videoService.pressGoodButton(userId, videoId);
    }

    @GetMapping("video/{videoId}/bad/user/{userId}")
    public VideoGoodBadStatusMapper pressBad(@PathVariable("userId") final Long userId,
                                              @PathVariable("videoId") final Long videoId) {
        return videoService.pressBadButton(userId, videoId);
    }
}
