package com.shallwe.domain.experiencegift.dto.response;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.Explanation;
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

    private List<String> giftImgUrl;
    private Long ExperienceGiftId;
    private String title;
    private String subtitle;
    private Long price;
    private List<ExplanationRes> explanation;
    private String description;
    private String expCategory;
    private String sttCategory;

    public static ExperienceDetailRes toDto(ExperienceGift experienceGift,List<String> giftImgUrl){
        return ExperienceDetailRes.builder()
                .ExperienceGiftId(experienceGift.getId())
                .title(experienceGift.getTitle())
                .price(experienceGift.getPrice())
                .description(experienceGift.getDescription())
                .subtitle(experienceGift.getSubtitle().getTitle())
                .expCategory(experienceGift.getExperienceCategory().getExpCategory())
                .sttCategory(experienceGift.getSituationCategory().getSttCategory())
                .giftImgUrl(giftImgUrl)
                .build();
    }

    public static ExperienceDetailRes toDetailDto(ExperienceGift experienceGift, List<Explanation> explanations,List<String> giftImgUrl){
        ExperienceDetailRes experienceDetailRes=new ExperienceDetailRes();
        experienceDetailRes.ExperienceGiftId=experienceGift.getId();
        experienceDetailRes.giftImgUrl= giftImgUrl;
        experienceDetailRes.title=experienceGift.getTitle();
        experienceDetailRes.subtitle=experienceGift.getSubtitle().getTitle();
        experienceDetailRes.price=experienceGift.getPrice();
        experienceDetailRes.explanation=explanations.stream().map(m -> ExplanationRes.toDto(m.getStage(),m.getDescription(), m.getExplanationKey())).collect(Collectors.toList());
        return experienceDetailRes;
    }

}
