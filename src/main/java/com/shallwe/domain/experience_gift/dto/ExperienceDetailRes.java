package com.shallwe.domain.experience_gift.dto;

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
    private Subtitle subtitle;
    private Long price;
    private String description;

    public static ExperienceDetailRes toDto(ExperienceGift experienceGift){
        return ExperienceDetailRes.builder()
                .ExperienceGiftId(experienceGift.getExperienceGiftId())
                .title(experienceGift.getTitle())
                .thumbnail(experienceGift.getThumbnail())
                .price(experienceGift.getPrice())
                .description(experienceGift.getDescription())
                .subtitle(experienceGift.getSubtitle())
                .build();
    }
}
