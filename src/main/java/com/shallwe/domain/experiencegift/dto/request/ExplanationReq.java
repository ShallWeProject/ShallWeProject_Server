package com.shallwe.domain.experiencegift.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExplanationReq {
    private String stage;
    private String description;
    private String explanationUrl;
}
