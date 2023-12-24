package com.shallwe.domain.experiencegift.dto.response;

import com.shallwe.domain.experiencegift.domain.SituationCategory;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;
import lombok.Builder;
import lombok.Data;

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

    public static SttCategoryRes toDto(SituationCategory situationCategory){
        return SttCategoryRes.builder()
                .sttCategoryId(situationCategory.getId())
                .sttCategory(situationCategory.getSttCategory())
                .imageUrl(AwsS3ImageUrlUtil.toUrl(situationCategory.getImageKey()))
                .build();
    }

}
