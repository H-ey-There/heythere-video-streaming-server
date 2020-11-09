package com.heythere.video.video.model;

import com.heythere.video.video.message.domain.UserEventDto;
import com.heythere.video.video.message.domain.UserMessageDto;
import com.heythere.video.video.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Unique
    private String email;
    private String name;
    private String img;

    @OneToMany(mappedBy = "user")
    private List<VideoAndUser> videoStatus = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CommentAndUser> commentStatus = new ArrayList<>();
    @Builder
    public User(Long id,
                String email,
                String name,
                String img,
                List<VideoAndUser> videoStatus,
                List<CommentAndUser> commentStatus) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.img = img;
        this.videoStatus = videoStatus;
        this.commentStatus = commentStatus;
    }

    public User update(UserEventDto userEvent) {
        final UserMessageDto message = userEvent.getUserMessageDto();

        this.email = message.getEmail();
        this.name = message.getName();
        this.img = message.getImg();

        return this;
    }
}
