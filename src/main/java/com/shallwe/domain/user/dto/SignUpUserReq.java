package com.shallwe.domain.user.dto;

import com.shallwe.domain.user.domain.Gender;
import lombok.Data;

@Data
public class SignUpUserReq {

    private Boolean marketingConsent;

    private Integer age;

    private Gender gender;

}
