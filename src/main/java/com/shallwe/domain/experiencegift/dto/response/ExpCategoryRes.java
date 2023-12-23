package com.shallwe.domain.experiencegift.dto.response;

import com.shallwe.domain.experiencegift.domain.ExperienceCategory;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;
import lombok.Builder;
import lombok.Data;

@Data
public class ExpCategoryRes {
    private Long expCategoryId;
    private String expCategory;
    private String imageUrl;

    @Builder
    public ExpCategoryRes(Long expCategoryId,String expCategory,String imageUrl){
        this.expCategoryId=expCategoryId;
        this.expCategory=expCategory;
        this.imageUrl=imageUrl;
    }

    public static ExpCategoryRes toDto(ExperienceCategory experienceCategory){
        return ExpCategoryRes.builder()
                .expCategoryId(experienceCategory.getId())
                .expCategory(experienceCategory.getExpCategory())
                .imageUrl(AwsS3ImageUrlUtil.toUrl(experienceCategory.getImageKey()))
                .build();
    }

}