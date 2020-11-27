package com.heythere.video.video.model;

import com.heythere.video.video.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Video extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;

    private String title;
    private String description;
    private String thumbnailUrl;

    @Column(columnDefinition = "TEXT")
    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "video")
    private List<Comment> comments = new ArrayList<>();

    private Integer viewCount;
    private Integer goodCount;
    private Integer badCount;

    @PrePersist
    public void perPersist() {
        viewCount = viewCount == null ? 0 : viewCount;
        goodCount = goodCount == null ? 0 : goodCount;
        badCount = badCount == null ? 0 : badCount;
    }

    @Builder
    public Video(Long id,
                 String title,
                 String description,
                 String thumbnailUrl,
                 String videoUrl,
                 User user,
                 List<Comment> comments,
                 Integer viewCount,
                 Integer goodCount,
                 Integer badCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.videoUrl = videoUrl;
        this.user = user;
        this.comments = comments;
        this.viewCount = viewCount;
        this.goodCount = goodCount;
        this.badCount = badCount;
    }

    public Video updateViewCount() {
        this.viewCount++;
        return this;
    }

    public void increaseGood() {
        this.goodCount++;
    }

    public void decreaseGood() {
        this.goodCount--;
    }

    public void increaseBad() {
        this.badCount++;
    }

    public void decreaseBad() {
        this.badCount--;
    }
}
