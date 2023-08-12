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
public class ExperienceDetailRes {

    private Long ExperienceGiftId;
    private String thumbnail;
    private String title;
    private String subtitle;
    private Long price;
    private String giftImgUrl;
    private String description;
    private String expCategory;
    private String sttCategory;



    public static ExperienceDetailRes toDto(ExperienceGift experienceGift){
        return ExperienceDetailRes.builder()
                .ExperienceGiftId(experienceGift.getExperienceGiftId())
                .title(experienceGift.getTitle())
                .thumbnail(experienceGift.getThumbnail())
                .price(experienceGift.getPrice())
                .description(experienceGift.getDescription())
                .subtitle(experienceGift.getSubtitle().getTitle())
                .expCategory(experienceGift.getExpCategory().getExpCategory())
                .sttCategory(experienceGift.getSttCategory().getSttCategory())
                .giftImgUrl(experienceGift.getGiftImgKey())
                .build();
    }

}
