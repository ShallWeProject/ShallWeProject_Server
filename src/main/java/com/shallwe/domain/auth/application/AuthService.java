package com.shallwe.domain.auth.application;

import java.net.URI;
import java.util.Optional;

import com.shallwe.domain.auth.dto.*;
import com.shallwe.domain.auth.exception.AlreadyExistEmailException;
import com.shallwe.global.DefaultAssert;
import com.shallwe.global.config.security.token.UserPrincipal;

import com.shallwe.domain.user.domain.Provider;
import com.shallwe.domain.user.domain.Role;
import com.shallwe.domain.auth.domain.Token;
import com.shallwe.domain.user.domain.User;
import com.shallwe.global.error.DefaultAuthenticationException;
import com.shallwe.global.payload.ApiResponse;
import com.shallwe.global.payload.ErrorCode;
import com.shallwe.global.payload.Message;
import com.shallwe.domain.auth.domain.repository.TokenRepository;
import com.shallwe.domain.user.domain.repository.UserRepository;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class AuthService {

    private final CustomTokenProviderService customTokenProviderService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public AuthRes signUp(SignUpReq signUpReq) {
        if (userRepository.existsByEmail(signUpReq.getEmail()))
            throw new AlreadyExistEmailException();

        User newUser = User.builder()
                .providerId(signUpReq.getProviderId())
                .provider(Provider.kakao)
                .name(signUpReq.getNickname())
                .email(signUpReq.getEmail())
                .profileImgUrl(signUpReq.getProfileImgUrl())
                .password(passwordEncoder.encode(signUpReq.getProviderId()))
                .role(Role.USER)
                .build();

        userRepository.save(newUser);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signUpReq.getEmail(),
                        signUpReq.getProviderId()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenMapping tokenMapping = customTokenProviderService.createToken(authentication);
        Token token = Token.builder()
                .refreshToken(tokenMapping.getRefreshToken())
                .userEmail(tokenMapping.getUserEmail())
                .build();
        tokenRepository.save(token);

        return AuthRes.builder()
                .accessToken(tokenMapping.getAccessToken())
                .refreshToken(tokenMapping.getRefreshToken())
                .build();
    }

    @Transactional
    public AuthRes signIn(SignInReq signInReq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInReq.getEmail(),
                        signInReq.getProviderId()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenMapping tokenMapping = customTokenProviderService.createToken(authentication);
        Token token = Token.builder()
                .refreshToken(tokenMapping.getRefreshToken())
                .userEmail(tokenMapping.getUserEmail())
                .build();
        tokenRepository.save(token);

        return AuthRes.builder()
                .accessToken(tokenMapping.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

    public AuthRes refresh(RefreshTokenReq tokenRefreshRequest) {
        //1차 검증
        boolean checkValid = valid(tokenRefreshRequest.getRefreshToken());
        DefaultAssert.isAuthentication(checkValid);

        Token token = tokenRepository.findByRefreshToken(tokenRefreshRequest.getRefreshToken())
                .orElseThrow(() -> new DefaultAuthenticationException(ErrorCode.INVALID_AUTHENTICATION));
        Authentication authentication = customTokenProviderService.getAuthenticationByEmail(token.getUserEmail());

        //4. refresh token 정보 값을 업데이트 한다.
        //시간 유효성 확인
        TokenMapping tokenMapping;

        Long expirationTime = customTokenProviderService.getExpiration(tokenRefreshRequest.getRefreshToken());
        if (expirationTime > 0) {
            tokenMapping = customTokenProviderService.refreshToken(authentication, token.getRefreshToken());
        } else {
            tokenMapping = customTokenProviderService.createToken(authentication);
        }

        Token updateToken = token.updateRefreshToken(tokenMapping.getRefreshToken());
        tokenRepository.save(updateToken);

        AuthRes authResponse = AuthRes.builder().accessToken(tokenMapping.getAccessToken()).refreshToken(updateToken.getRefreshToken()).build();

        return authResponse;
    }

    public Message signOut(RefreshTokenReq tokenRefreshRequest) {
        boolean checkValid = valid(tokenRefreshRequest.getRefreshToken());
        DefaultAssert.isAuthentication(checkValid);

        //4 token 정보를 삭제한다.
        Token token = tokenRepository.findByRefreshToken(tokenRefreshRequest.getRefreshToken())
                .orElseThrow(() -> new DefaultAuthenticationException(ErrorCode.INVALID_AUTHENTICATION));
        tokenRepository.delete(token);

        return Message.builder()
                .message("로그아웃 하였습니다.")
                .build();
    }

    private boolean valid(String refreshToken) {
        //1. 토큰 형식 물리적 검증
        boolean validateCheck = customTokenProviderService.validateToken(refreshToken);
        DefaultAssert.isTrue(validateCheck, "Token 검증에 실패하였습니다.");

        //2. refresh token 값을 불러온다.
        Optional<Token> token = tokenRepository.findByRefreshToken(refreshToken);
        DefaultAssert.isTrue(token.isPresent(), "탈퇴 처리된 회원입니다.");

        //3. email 값을 통해 인증값을 불러온다
        Authentication authentication = customTokenProviderService.getAuthenticationByEmail(token.get().getUserEmail());
        DefaultAssert.isTrue(token.get().getUserEmail().equals(authentication.getName()), "사용자 인증에 실패하였습니다.");

        return true;
    }

}
