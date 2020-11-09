package com.heythere.video.video.repository;

import com.heythere.video.video.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
