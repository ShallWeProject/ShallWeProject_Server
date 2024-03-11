package com.shallwe.domain.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oauth2_token")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class OAuth2Token {

    @Id
    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Builder
    public OAuth2Token(String providerId, String refreshToken) {
        this.providerId = providerId;
        this.refreshToken = refreshToken;
    }

}
