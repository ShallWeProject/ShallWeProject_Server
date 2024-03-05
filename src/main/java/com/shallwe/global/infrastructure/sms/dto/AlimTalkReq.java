package com.shallwe.global.infrastructure.sms.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class AlimTalkReq {

    private String plusFriendId;
    private String templateCode;
    private List<MessageMapping> messages;

    @Builder
    public AlimTalkReq(String plusFriendId, String templateCode, List<MessageMapping> messages) {
        this.plusFriendId = plusFriendId;
        this.templateCode = templateCode;
        this.messages = messages;
    }

}
