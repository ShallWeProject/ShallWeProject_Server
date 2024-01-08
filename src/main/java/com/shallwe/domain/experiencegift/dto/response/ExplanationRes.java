package com.shallwe.domain.experiencegift.dto.response;

import com.shallwe.global.utils.AwsS3ImageUrlUtil;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ExplanationRes {

    private String stage;
    private String description;
    private String explanationUrl;

    @Builder
    public ExplanationRes(String stage,String description,String explanationUrl){
        this.stage=stage;
        this.description=description;
        this.explanationUrl=explanationUrl;
    }

    public static ExplanationRes toDto(String stage,String description,String explanationUrl){
        return ExplanationRes.builder()
                .stage(stage)
                .description(description)
                .explanationUrl(AwsS3ImageUrlUtil.toUrl(explanationUrl))
                .build();

    }

}
