package com.shallwe.domain.experience_gift.dto.response;

import com.shallwe.domain.experience_gift.domain.ExpCategory;
import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import com.shallwe.domain.experience_gift.domain.SttCategory;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SttCategoryRes {
    private Long sttCategoryId;
    private String sttCategory;
    private String imageUrl;

    @Builder
    public SttCategoryRes(Long sttCategoryId,String sttCategory,String imageUrl){
        this.sttCategoryId=sttCategoryId;
        this.sttCategory=sttCategory;
        this.imageUrl=imageUrl;
    }

    public static SttCategoryRes toDto(SttCategory sttCategory){
        return SttCategoryRes.builder()
                .sttCategoryId(sttCategory.getSttCategoryId())
                .sttCategory(sttCategory.getSttCategory())
                .imageUrl(AwsS3ImageUrlUtil.toUrl(sttCategory.getImageKey()))
                .build();
    }
}
