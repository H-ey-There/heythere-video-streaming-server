package com.heythere.video.video.model;

import com.heythere.video.video.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(
        name = "video_and_user",
        uniqueConstraints =  {
                @UniqueConstraint(
                        columnNames = {"user_id", "video_id"}
                )
        }
)
@Entity
public class VideoAndUser extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_user_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    private Boolean goodStatus;
    private Boolean badStatus;

    @PrePersist
    public void prePersist() {
        if (goodStatus == null && badStatus == null) {
            goodStatus = false;
            badStatus = false;
        }
    }

    @Builder
    public VideoAndUser(Long id, User user, Video video, Boolean goodStatus, Boolean badStatus) {
        this.id = id;
        this.user = user;
        this.video = video;
        this.goodStatus = goodStatus;
        this.badStatus = badStatus;
    }

    public VideoAndUser updateGoodStatus() {
        this.goodStatus = !goodStatus;
        return this;
    }

    public VideoAndUser updateBadStatus() {
        this.badStatus = !badStatus;
        return this;
    }
}
