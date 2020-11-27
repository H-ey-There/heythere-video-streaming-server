package com.heythere.video.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LargeCommentRegisterRequestDto {
    @NotNull
    private Long requestUserId;
    @NotNull
    private Long commentId;
    @NotNull
    private String largeComment;
}
