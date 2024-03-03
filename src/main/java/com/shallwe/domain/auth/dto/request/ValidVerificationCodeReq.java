package com.shallwe.domain.auth.dto.request;

import lombok.Data;

@Data
public class ValidVerificationCodeReq {

    private String verificationCode;
    private String phoneNumber;

}
