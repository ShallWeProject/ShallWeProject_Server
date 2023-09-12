package com.shallwe.domain.experiencegift.dto.request;

import com.shallwe.domain.experiencegift.domain.ExpCategory;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.SttCategory;
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
    public static ExperienceGift toEntity(ExperienceReq experienceReq,Subtitle subtitle, ExpCategory expCategory,SttCategory sttCategory ){
        return ExperienceGift.builder()
                .title(experienceReq.getTitle())
                .thumbnail(experienceReq.getThumbnail())
                .price(experienceReq.getPrice())
                .description(experienceReq.getDescription())
                .subtitle(subtitle)
                .expCategory(expCategory)
                .sttCategory(sttCategory)
                .build();
    }

}
