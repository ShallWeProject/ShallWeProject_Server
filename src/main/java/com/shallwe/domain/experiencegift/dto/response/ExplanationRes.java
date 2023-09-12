package com.shallwe.domain.experiencegift.dto.response;

import com.shallwe.global.utils.AwsS3ImageUrlUtil;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ExplanationRes {
    private String description;
    private String explanationUrl;

    @Builder
    public ExplanationRes(String description,String explanationUrl){
        this.description=description;
        this.explanationUrl=explanationUrl;
    }

    public static ExplanationRes toDto(String description,String explanationUrl){
        return ExplanationRes.builder()
                .description(description)
                .explanationUrl(AwsS3ImageUrlUtil.toUrl(explanationUrl))
                .build();

    }

}
