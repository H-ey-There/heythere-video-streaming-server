package com.heythere.video.video.repository;


import com.heythere.video.video.model.User;
import com.heythere.video.video.model.Video;
import com.heythere.video.video.model.VideoAndUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoAndUserRepository extends JpaRepository<VideoAndUser,Long> {
    boolean existsByUserAndVideo(final User user, final Video video );
    Optional<VideoAndUser> findByUserAndVideo(final User user, final Video video);
}
