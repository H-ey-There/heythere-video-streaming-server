package com.heythere.video.video.model;

import com.heythere.video.video.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class LargeComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "large_comment_id")
    private Long id;

    private String largeComment;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public LargeComment(Long id, String largeComment, Comment comment, User user) {
        this.id = id;
        this.largeComment = largeComment;
        this.comment = comment;
        this.user = user;
    }

    public LargeComment addLargeCommentToComment(Comment comment) {
        comment.getLargeComments().add(this);
        return this;
    }
}
