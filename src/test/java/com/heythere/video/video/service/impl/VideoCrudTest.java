package com.heythere.video.video.service.impl;

import com.heythere.video.video.model.Comment;
import com.heythere.video.video.model.User;
import com.heythere.video.video.model.Video;
import com.heythere.video.video.repository.CommentRepository;
import com.heythere.video.video.repository.LargeCommentRepository;
import com.heythere.video.video.repository.UserRepository;
import com.heythere.video.video.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class VideoCrudTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    LargeCommentRepository largeCommentRepository;

    @BeforeEach
    public void loadData() {
        userRepository.saveAndFlush(User.builder()
                .email("hsm012362@gmail.com")
                .name("min")
                .img("img")
                .build());
    }

    @Test
    public void 비디오_업로드() {
        //given
        final Long requestUserId = 1L;
        final User user = userRepository.findById(requestUserId).orElse(null);
        assertNotNull(user);

        final String title = "test";
        final String desc = "test desc";
        final String videoUrl = "test video url";
        final String thumbnailUrl = "thumbnail url";

        //when
        final Video video = videoRepository.saveAndFlush(Video.builder()
                .title(title)
                .description(desc)
                .videoUrl(videoUrl)
                .thumbnailUrl(thumbnailUrl)
                .user(user)
                .build());
        assertNotNull(video);

        //then
        assertEquals(1L, video.getId());
        assertEquals(0, video.getViewCount());
        assertEquals(0, video.getGoodCount());
        assertEquals(0, video.getBadCount());
        assertEquals(title, video.getTitle());
        assertEquals(desc, video.getDescription());
        assertEquals(videoUrl, video.getVideoUrl());
        assertEquals(thumbnailUrl, video.getThumbnailUrl());

        videoRepository.deleteById(video.getId());
    }

    @Test
    void 비디오_댓글_달기() {
        final Long requestUserId = 1L;
        final User user = userRepository.findById(requestUserId).orElse(null);
        assertNotNull(user);

        final Video video = videoRepository.saveAndFlush(Video.builder()
                .title("test")
                .description("test desc")
                .videoUrl("test video url")
                .thumbnailUrl("thumbnail url")
                .user(user)
                .build());
        System.out.println("video id " + video.getId());
        assertNotNull(video);

        final Long targetVideoId = 1L;
        final String requestComment = "test comment";

        //when
        final Comment comment = commentRepository.saveAndFlush(
                Comment.builder()
                        .user(user)
                        .comment(requestComment)
                        .build()
        );
        assertNotNull(comment);
        System.out.println("comment id : " + comment.getId());

        assertNotNull(comment.addCommentToVideo(video));

        //then
        assertEquals(1L, comment.getId());
        assertEquals(targetVideoId, comment.getVideo().getId());
        assertEquals(requestUserId, comment.getUser().getId());
        assertEquals(requestComment, comment.getComment());
        assertEquals(1, video.getComments().size());
    }

}
