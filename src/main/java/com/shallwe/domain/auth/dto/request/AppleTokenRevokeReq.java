package com.shallwe.domain.auth.dto.request;

import lombok.Data;

@Data
public class AppleTokenRevokeReq {

    private String client_id;
    private String client_secret;
    private String token;

    public static AppleTokenRevokeReq of(String clientId, String clientSecret, String token) {
        AppleTokenRevokeReq request = new AppleTokenRevokeReq();
        request.client_id = clientId;
        request.client_secret = clientSecret;
        request.token = token;
        return request;
    }

}