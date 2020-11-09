package com.heythere.video.video.repository;

import com.heythere.video.video.model.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query("select v from Video v order by v.viewCount desc")
    List<Video> findVideosTopViewCountLimitTen(final Pageable pageable);

    @Query("select v from Video v order by v.goodCount desc")
    List<Video> findVideosTopGoodCountLimitTen(final Pageable pageable);

}


