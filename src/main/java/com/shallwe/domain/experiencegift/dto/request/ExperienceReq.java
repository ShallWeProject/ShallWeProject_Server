package com.shallwe.domain.experiencegift.dto.request;

import com.shallwe.domain.experiencegift.domain.ExperienceCategory;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.SituationCategory;
import com.shallwe.domain.experiencegift.domain.Subtitle;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExperienceReq {
    private String title;
    private String thumbnail;
    private Long price;
    private Long expCategoryId;
    private Long sttCategoryId;
    private Long subtitleId;
    private String description;


    @Builder
    public static ExperienceGift toEntity(ExperienceReq experienceReq, Subtitle subtitle, ExperienceCategory experienceCategory, SituationCategory situationCategory){
        return ExperienceGift.builder()
                .title(experienceReq.getTitle())
                .thumbnail(experienceReq.getThumbnail())
                .price(experienceReq.getPrice())
                .description(experienceReq.getDescription())
                .subtitle(subtitle)
                .experienceCategory(experienceCategory)
                .situationCategory(situationCategory)
                .build();
    }

}
