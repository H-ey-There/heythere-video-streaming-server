package com.heythere.video.video.mapper;

import com.heythere.video.video.model.User;
import com.heythere.video.video.model.Video;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class VideoResponseMapper {
    private final Long id;
    private final String title;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String thumbnailUrl;
    private final String videoUrl;
    private final int viewCount;
    private final int good;
    private final int bad;
    private final boolean goodStatus;
    private final boolean badStatus;

    private final Long userId;
    private final String email;
    private final String name;
    private final String userImg;

    private final List<CommentResponseMapper> comments;

    @Builder
    public VideoResponseMapper(Long id,
                               String title,
                               String description,
                               LocalDateTime createdAt,
                               LocalDateTime modifiedAt,
                               String thumbnailUrl,
                               String videoUrl,
                               int viewCount,
                               int good,
                               int bad,
                               boolean goodStatus,
                               boolean badStatus,
                               Long userId,
                               String email,
                               String name,
                               String userImg,
                               List<CommentResponseMapper> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.thumbnailUrl = thumbnailUrl;
        this.videoUrl = videoUrl;
        this.viewCount = viewCount;
        this.good = good;
        this.bad = bad;
        this.goodStatus = goodStatus;
        this.badStatus = badStatus;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.userImg = userImg;
        this.comments = comments;
    }

    public static VideoResponseMapper of(final Video video,
                                         final Boolean goodStatus,
                                         final Boolean badStatus) {
        final User user = video.getUser();

        return VideoResponseMapper.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .createdAt(video.getCreatedAt())
                .modifiedAt(video.getModifiedAt())
                .thumbnailUrl(video.getThumbnailUrl())
                .videoUrl(video.getVideoUrl())
                .viewCount(video.getViewCount())
                .good(video.getGoodCount())
                .bad(video.getBadCount())
                .goodStatus(goodStatus)
                .badStatus(badStatus)
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .userImg(user.getImg())
                .comments(video.getComments().stream()
                        .map(CommentResponseMapper::of)
                        .sorted((c1,c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                        .collect(Collectors.toList())
                )
                .build();
    }

    public static VideoResponseMapper of(final Video video) {
        final User user = video.getUser();

        return VideoResponseMapper.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .createdAt(video.getCreatedAt())
                .modifiedAt(video.getModifiedAt())
                .thumbnailUrl(video.getThumbnailUrl())
                .videoUrl(video.getVideoUrl())
                .viewCount(video.getViewCount())
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .userImg(user.getImg())
                .build();
    }
}
