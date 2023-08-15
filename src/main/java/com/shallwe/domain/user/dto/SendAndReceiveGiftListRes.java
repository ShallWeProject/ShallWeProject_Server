package com.shallwe.domain.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SendAndReceiveGiftListRes {

    private List<SendAndReceiveGiftDetailRes> gifts;

    @Builder
    public SendAndReceiveGiftListRes(List<SendAndReceiveGiftDetailRes> gifts) {
        this.gifts = gifts;
    }

}
