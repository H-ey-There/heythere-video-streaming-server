package com.heythere.video.video.service.impl;

import com.heythere.video.video.model.User;
import com.heythere.video.video.repository.CommentRepository;
import com.heythere.video.video.repository.LargeCommentRepository;
import com.heythere.video.video.repository.UserRepository;
import com.heythere.video.video.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VideoServiceImplTest {

    @Autowired UserRepository userRepository;
    @Autowired VideoRepository videoRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired LargeCommentRepository largeCommentRepository;

    @Test
    void userTest() {
        userRepository.saveAndFlush(new User("aa@aa", "img", "min"));
        List<User> users = userRepository.findAll();
        assertNotNull(users);
        assertEquals(1L, users.get(0).getId());
        assertEquals(1, users.size());
        userRepository.deleteAll();
    }

      
    @Test
    void findVideoById() {

    }

      
    @Test
    void findAllVideos() {
    }


      
    @Test
    void uploadVideo() {
    }

      
    @Test
    void registerComment() {
    }

      
    @Test
    void registerLargeComment() {
    }

      
    @Test
    void findVideosTopViewCountLimitTen() {
    }

      
    @Test
    void findVideosTopGoodCountLimitTen() {
    }
}