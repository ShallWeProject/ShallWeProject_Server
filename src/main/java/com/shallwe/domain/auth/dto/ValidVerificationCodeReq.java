package com.shallwe.domain.auth.dto;

import lombok.Data;

@Data
public class ValidVerificationCodeReq {

    private String verificationCode;
    private String phoneNumber;

}
