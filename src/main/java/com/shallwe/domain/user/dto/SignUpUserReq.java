package com.shallwe.domain.user.dto;

import com.shallwe.domain.user.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignUpUserReq {

    @Schema(type = "string", example = "01012345678", description = "전화번호")
    private String phoneNumber;
    @Schema(type = "boolean", example = "true / false", description = "마켓팅 수신 동의 여부")
    private Boolean marketingConsent;
    @Schema(type = "int", example = "25", description = "나이")
    private Integer age;
    @Schema(type = "string", example = "MALE / FEMALE / UNKNOWN", description = "성별")
    private Gender gender;

}
