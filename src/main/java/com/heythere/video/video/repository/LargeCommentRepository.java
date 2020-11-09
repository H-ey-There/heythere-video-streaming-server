package com.heythere.video.video.repository;

import com.heythere.video.video.model.LargeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LargeCommentRepository extends JpaRepository<LargeComment, Long> {
}
