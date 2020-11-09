package com.heythere.video.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class CommentRegisterRequestDto {
    @NotNull
    private final Long requestUserId;
    @NotNull
    private final Long videoId;
    @NotNull
    private final String comment;
}
