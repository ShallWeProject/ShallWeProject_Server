package com.shallwe.domain.auth.exception;

public class InvalidOAuth2RefreshTokenException extends RuntimeException {

        public InvalidOAuth2RefreshTokenException() {
            super("유효하지 않은 oauth2 refresh token입니다.");
        }

}
