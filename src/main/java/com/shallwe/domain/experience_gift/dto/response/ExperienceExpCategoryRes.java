package com.shallwe.domain.experience_gift.dto.response;

import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import com.shallwe.domain.experience_gift.domain.Subtitle;
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
    private Subtitle subtitle;
    private String title;
    private Long price;
    private String giftImgUrl;

    public static ExperienceExpCategoryRes toDto(ExperienceGift experienceGift){
        return ExperienceExpCategoryRes.builder()
                .expCategoryId(experienceGift.getExpCategory().getExpCategoryId())
                .ExperienceGiftId(experienceGift.getExperienceGiftId())
                .thumbnail(experienceGift.getThumbnail())
                .subtitle(experienceGift.getSubtitle())
                .title(experienceGift.getTitle())
                .price(experienceGift.getPrice())
                .giftImgUrl(experienceGift.getGiftImgKey())
                .build();
    }

}
