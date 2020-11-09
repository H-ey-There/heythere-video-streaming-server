package com.heythere.video.video.model;

import com.heythere.video.video.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(
        name = "comment_and_user",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "comment_id"}
                )
        }
)
@Entity
public class CommentAndUser extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

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
    public CommentAndUser(Long id,
                          User user,
                          Comment comment,
                          Boolean goodStatus,
                          Boolean badStatus) {
        this.id = id;
        this.user = user;
        this.comment = comment;
        this.goodStatus = goodStatus;
        this.badStatus = badStatus;
    }

    public CommentAndUser addStatusToUserAndComment(final User user, final Comment comment) {
        user.getCommentStatus().add(this);
        comment.getStatus().add(this);
        return this;
    }
}

