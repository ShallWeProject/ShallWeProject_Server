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
public class ExperienceRes {

    private Long ExperienceGiftId;
    private String thumbnail;
    private Subtitle subtitle;
    private String title;
    private Long price;
    private String giftImgUrl;

    public static ExperienceRes toDto(ExperienceGift experienceGift){
        return ExperienceRes.builder()
                .ExperienceGiftId(experienceGift.getExperienceGiftId())
                .thumbnail(experienceGift.getThumbnail())
                .subtitle(experienceGift.getSubtitle())
                .title(experienceGift.getTitle())
                .price(experienceGift.getPrice())
                .giftImgUrl(experienceGift.getGiftImgKey())
                .build();
    }

}
