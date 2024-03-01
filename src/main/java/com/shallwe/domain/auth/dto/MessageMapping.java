package com.shallwe.domain.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MessageMapping {

    private String to;
    private String content;

    @Builder
    public MessageMapping(String to, String content) {
        this.to = to;
        this.content = content;
    }

}
