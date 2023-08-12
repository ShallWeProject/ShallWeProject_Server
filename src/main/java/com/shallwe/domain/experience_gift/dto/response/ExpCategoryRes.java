package com.shallwe.domain.experience_gift.dto.response;

import com.shallwe.domain.experience_gift.domain.ExpCategory;
import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
public class ExpCategoryRes {
    private Long expCategoryId;
    private String expCategory;

    @Builder
    public ExpCategoryRes(Long expCategoryId,String expCategory){
        this.expCategoryId=expCategoryId;
        this.expCategory=expCategory;
    }

    public static ExpCategoryRes toDto(ExpCategory expCategory){
        return ExpCategoryRes.builder()
                .expCategoryId(expCategory.getExpCategoryId())
                .expCategory(expCategory.getExpCategory())
                .build();
    }
}