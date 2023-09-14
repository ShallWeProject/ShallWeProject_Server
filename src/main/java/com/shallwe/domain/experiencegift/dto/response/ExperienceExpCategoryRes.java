package com.shallwe.domain.experiencegift.dto.response;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ExperienceExpCategoryRes {

    private Long expCategoryId;
    private Long ExperienceGiftId;
    private String thumbnail;
    private String subtitleTitle;
    private String title;
    private Long price;
    private String giftImgUrl;

    public static ExperienceExpCategoryRes toDto(ExperienceGift experienceGift){
        return ExperienceExpCategoryRes.builder()
                .expCategoryId(experienceGift.getExpCategory().getExpCategoryId())
                .ExperienceGiftId(experienceGift.getExperienceGiftId())
                .thumbnail(experienceGift.getThumbnail())
                .subtitleTitle(experienceGift.getSubtitle().getTitle())
                .title(experienceGift.getTitle())
                .price(experienceGift.getPrice())
                .giftImgUrl(AwsS3ImageUrlUtil.toUrl(experienceGift.getGiftImgKey()))
                .build();
    }

}
