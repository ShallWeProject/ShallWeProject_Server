package com.shallwe.domain.experiencegift.dto.response;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ExperienceRes {

    private Long ExperienceGiftId;
    private String thumbnail;
    private String subtitle;
    private String title;
    private Long price;
    private List<String> giftImgUrl;

    public static ExperienceRes toDto(ExperienceGift experienceGift, List<String> experienceGiftImgs){
        return ExperienceRes.builder()
                .ExperienceGiftId(experienceGift.getId())
                .thumbnail(experienceGift.getThumbnail())
                .subtitle(experienceGift.getSubtitle().getTitle())
                .title(experienceGift.getTitle())
                .price(experienceGift.getPrice())
                .giftImgUrl(experienceGiftImgs)
                .build();
    }

}
