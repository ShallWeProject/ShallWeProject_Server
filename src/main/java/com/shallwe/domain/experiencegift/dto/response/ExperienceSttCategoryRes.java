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
public class ExperienceSttCategoryRes {

    private Long sttCategoryId;
    private Long ExperienceGiftId;
    private String subtitleTitle;
    private String title;
    private Long price;
    private List<String> giftImgUrl;

    public static ExperienceSttCategoryRes toDto(ExperienceGift experienceGift,List<String> giftImgUrl){
        return ExperienceSttCategoryRes.builder()
                .ExperienceGiftId(experienceGift.getSttCategory().getSttCategoryId())
                .ExperienceGiftId(experienceGift.getExperienceGiftId())
                .subtitleTitle(experienceGift.getSubtitle().getTitle())
                .title(experienceGift.getTitle())
                .price(experienceGift.getPrice())
                .giftImgUrl(giftImgUrl)
                .build();
    }

}
