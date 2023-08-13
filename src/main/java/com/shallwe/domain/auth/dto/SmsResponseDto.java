package com.shallwe.domain.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmsResponseDto {

    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;

}
