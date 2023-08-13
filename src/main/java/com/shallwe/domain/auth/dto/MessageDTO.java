package com.shallwe.domain.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MessageDTO {

    private String to;
    private String content;

    @Builder
    public MessageDTO(String to, String content) {
        this.to = to;
        this.content = content;
    }

}
