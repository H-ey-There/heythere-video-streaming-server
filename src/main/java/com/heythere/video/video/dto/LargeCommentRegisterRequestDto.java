package com.heythere.video.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class LargeCommentRegisterRequestDto {
    @NotNull
    private final Long requestUserId;
    @NotNull
    private final Long commentId;
    @NotNull
    private final String largeComment;
}
