package com.shallwe.domain.auth.dto.request;

import com.shallwe.domain.auth.dto.MessageMapping;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class NaverCloudSmsReq {

    private String type;
    private String contentType;
    private String countryCode;
    private String from;
    private String content;
    List<MessageMapping> messages;

    @Builder
    public NaverCloudSmsReq(String type, String contentType, String countryCode, String from, String content, List<MessageMapping> messages) {
        this.type = type;
        this.contentType = contentType;
        this.countryCode = countryCode;
        this.from = from;
        this.content = content;
        this.messages = messages;
    }

}
