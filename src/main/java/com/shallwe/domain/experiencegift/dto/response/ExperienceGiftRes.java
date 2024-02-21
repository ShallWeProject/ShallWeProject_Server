package com.shallwe.domain.experiencegift.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ExperienceGiftRes {

    private Long experienceGiftId;
    private String giftImgUrl;
    private String subtitle;
    private String title;
    private Long price;

    @QueryProjection
    public ExperienceGiftRes(Long experienceGiftId, String giftImgUrl, String subtitle, String title, Long price) {
        this.experienceGiftId = experienceGiftId;
        this.giftImgUrl = giftImgUrl;
        this.subtitle = subtitle;
        this.title = title;
        this.price = price;
    }

}
