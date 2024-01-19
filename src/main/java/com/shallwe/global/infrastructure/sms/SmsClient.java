package com.shallwe.global.infrastructure.sms;

import com.shallwe.domain.auth.dto.SmsResponseDto;
import com.shallwe.global.infrastructure.sms.dto.NaverVerifySmsReq;

public interface SmsClient {

    SmsResponseDto send(String receivePhoneNumber) throws Exception;

}
