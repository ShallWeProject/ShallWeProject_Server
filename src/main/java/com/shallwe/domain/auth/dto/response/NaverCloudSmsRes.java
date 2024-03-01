package com.shallwe.domain.auth.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NaverCloudSmsRes {

    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;

}
