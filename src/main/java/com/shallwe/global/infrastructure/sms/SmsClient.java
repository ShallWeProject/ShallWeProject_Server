package com.shallwe.global.infrastructure.sms;

import com.shallwe.domain.auth.dto.response.SmsResponseDto;

public interface SmsClient {

    SmsResponseDto send(String receivePhoneNumber) throws Exception;

}
