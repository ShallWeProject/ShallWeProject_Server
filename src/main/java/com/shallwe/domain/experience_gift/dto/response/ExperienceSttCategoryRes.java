package com.shallwe.domain.experience_gift.dto.response;

import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import com.shallwe.domain.experience_gift.domain.Subtitle;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ExperienceSttCategoryRes {

    private Long sttCategoryId;
    private Long ExperienceGiftId;
    private String thumbnail;
    private String subtitle;
    private String title;
    private Long price;
    private String giftImgUrl;

    public static ExperienceSttCategoryRes toDto(ExperienceGift experienceGift){
        return ExperienceSttCategoryRes.builder()
                .ExperienceGiftId(experienceGift.getSttCategory().getSttCategoryId())
                .ExperienceGiftId(experienceGift.getExperienceGiftId())
                .thumbnail(experienceGift.getThumbnail())
                .subtitle(experienceGift.getSubtitle().getTitle())
                .title(experienceGift.getTitle())
                .price(experienceGift.getPrice())
                .giftImgUrl(AwsS3ImageUrlUtil.toUrl(experienceGift.getGiftImgKey()))
                .build();
    }

}
