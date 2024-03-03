package com.shallwe.global.config.security;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
public class AuthConfig {
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();
    private final AppleAuth appleAuth = new AppleAuth();

    @Data
    public static class Auth {
        private String tokenSecret;
        private long accessTokenExpirationMsec;
        private long refreshTokenExpirationMsec;
    }

    @Data
    public static class AppleAuth {
        private String clientId;
        private String teamId;
        private String keyId;
        private String privateKey;
    }

    @Getter
    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

}
