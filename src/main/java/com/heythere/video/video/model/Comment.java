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
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @OneToMany(mappedBy = "comment")
    private List<LargeComment> largeComments = new ArrayList<>();

    @OneToMany(mappedBy = "comment")
    private List<CommentAndUser> status = new ArrayList<>();

    @Builder
    public Comment(Long id,
                   String comment,
                   User user,
                   Video video,
                   List<LargeComment> largeComments,
                   List<CommentAndUser> status) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.video = video;
        this.largeComments = largeComments;
        this.status = status;
    }

    public Comment addCommentToVideo(final Video video) {
        this.video = video;
        video.getComments().add(this);
        return this;
    }
}
