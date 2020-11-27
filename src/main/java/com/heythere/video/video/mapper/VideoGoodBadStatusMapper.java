package com.heythere.video.video.mapper;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VideoGoodBadStatusMapper {
    private Integer good;
    private Integer bad;
    private Boolean goodStatus;
    private Boolean badStatus;

    @Builder
    public VideoGoodBadStatusMapper(Integer good, Integer bad, Boolean goodStatus, Boolean badStatus) {
        this.good = good;
        this.bad = bad;
        this.goodStatus = goodStatus;
        this.badStatus = badStatus;
    }
}
