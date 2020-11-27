package com.heythere.video.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRegisterRequestDto {
    @NotNull
    private Long requestUserId;
    @NotNull
    private Long videoId;
    @NotNull
    private String comment;
}
