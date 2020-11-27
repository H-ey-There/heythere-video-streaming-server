package com.heythere.video.video.repository;

import com.heythere.video.video.model.CommentAndUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentAndUserRepository extends JpaRepository<CommentAndUser, Long> {
}
