package com.heythere.video.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Video extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;

    private String title;
    private String description;
    private String thumbnailUrl;
    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "video")
    private List<Comment> comments = new ArrayList<>();

    private Integer viewCount;
    private Integer likeCount;
    private Integer hateCount;

    @OneToMany(mappedBy = "video")
    private List<VideoAndUser> status = new ArrayList<>();


    @PrePersist
    public void perPersist() {
        viewCount = viewCount == null ? 0 : viewCount;
        likeCount = likeCount == null ? 0 : likeCount;
        hateCount = hateCount == null ? 0 : hateCount;
    }

    @Builder
    public Video(Long id,
                 String title,
                 String description,
                 String thumbnailUrl,
                 String videoUrl,
                 int viewCount,
                 User user,
                 List<Comment> comments,
                 List<VideoAndUser> status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.videoUrl = videoUrl;
        this.viewCount = viewCount;
        this.user = user;
        this.comments = comments;
        this.status = status;
    }
}
