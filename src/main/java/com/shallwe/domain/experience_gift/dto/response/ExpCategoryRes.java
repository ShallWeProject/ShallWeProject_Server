package com.shallwe.domain.experience_gift.dto.response;

import com.shallwe.domain.experience_gift.domain.ExpCategory;
import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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