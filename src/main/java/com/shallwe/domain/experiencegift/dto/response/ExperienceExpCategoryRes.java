package com.shallwe.domain.experiencegift.dto.response;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ExperienceExpCategoryRes {

    private Long expCategoryId;
    private Long ExperienceGiftId;
    private String subtitleTitle;
    private String title;
    private Long price;
    private List<String> giftImgUrl;

    public static ExperienceExpCategoryRes toDto(ExperienceGift experienceGift,List<String> giftImgUrl){
        return ExperienceExpCategoryRes.builder()
                .expCategoryId(experienceGift.getExpCategory().getExpCategoryId())
                .ExperienceGiftId(experienceGift.getExperienceGiftId())
                .subtitleTitle(experienceGift.getSubtitle().getTitle())
                .title(experienceGift.getTitle())
                .price(experienceGift.getPrice())
                .giftImgUrl(giftImgUrl)
                .build();
    }

}
