package com.shallwe.domain.experienceGift.dto.response;

import com.shallwe.domain.experienceGift.domain.ExpCategory;
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

    public static ExpCategoryRes toDto(ExpCategory expCategory){
        return ExpCategoryRes.builder()
                .expCategoryId(expCategory.getExpCategoryId())
                .expCategory(expCategory.getExpCategory())
                .imageUrl(AwsS3ImageUrlUtil.toUrl(expCategory.getImageKey()))
                .build();
    }
}