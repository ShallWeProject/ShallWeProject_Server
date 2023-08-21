package com.shallwe.domain.experience_gift.dto.response;

import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import com.shallwe.domain.experience_gift.domain.Explanation;
import com.shallwe.domain.experience_gift.domain.Subtitle;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ExperienceDetailRes {

    private String giftImgUrl;
    private Long ExperienceGiftId;
    private String thumbnail;
    private String title;
    private String subtitle;
    private Long price;
    private List<ExplanationRes> explanation;
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

    public static ExperienceDetailRes toDetailDto(ExperienceGift experienceGift, List<Explanation> explanations){
        ExperienceDetailRes experienceDetailRes=new ExperienceDetailRes();
        experienceDetailRes.ExperienceGiftId=experienceGift.getExperienceGiftId();
        experienceDetailRes.giftImgUrl= AwsS3ImageUrlUtil.toUrl(experienceGift.getGiftImgKey());
        experienceDetailRes.thumbnail=experienceGift.getThumbnail();
        experienceDetailRes.title=experienceGift.getTitle();
        experienceDetailRes.subtitle=experienceGift.getSubtitle().getTitle();
        experienceDetailRes.price=experienceGift.getPrice();
        experienceDetailRes.explanation=explanations.stream().map(m -> ExplanationRes.toDto(m.getDescription(), m.getExplanationKey())).collect(Collectors.toList());
        return experienceDetailRes;

    }

}
