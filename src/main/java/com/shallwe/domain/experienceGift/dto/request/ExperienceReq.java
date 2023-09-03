package com.shallwe.domain.experienceGift.dto.request;

import com.shallwe.domain.experienceGift.domain.ExpCategory;
import com.shallwe.domain.experienceGift.domain.ExperienceGift;
import com.shallwe.domain.experienceGift.domain.SttCategory;
import com.shallwe.domain.experienceGift.domain.Subtitle;
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
