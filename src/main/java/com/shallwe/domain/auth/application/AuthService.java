package com.shallwe.domain.auth.application;

import java.util.Optional;

import com.shallwe.domain.auth.dto.*;
import com.shallwe.domain.auth.exception.AlreadyExistEmailException;
import com.shallwe.domain.auth.exception.InvalidPasswordException;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.shopowner.domain.repository.ShopOwnerRepository;
import com.shallwe.domain.auth.dto.ShopOwnerChangePasswordReq;
import com.shallwe.domain.shopowner.exception.AlreadyExistPhoneNumberException;
import com.shallwe.domain.shopowner.exception.InvalidPhoneNumberException;
import com.shallwe.domain.shopowner.exception.InvalidShopOwnerException;
import com.shallwe.domain.user.exception.InvalidUserException;
import com.shallwe.global.DefaultAssert;

import com.shallwe.domain.user.domain.Provider;
import com.shallwe.domain.user.domain.Role;
import com.shallwe.domain.auth.domain.Token;
import com.shallwe.domain.user.domain.User;
import com.shallwe.global.error.DefaultAuthenticationException;
import com.shallwe.global.payload.ErrorCode;
import com.shallwe.global.payload.Message;
import com.shallwe.domain.auth.domain.repository.TokenRepository;
import com.shallwe.domain.user.domain.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final CustomTokenProviderService customTokenProviderService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final ShopOwnerRepository shopOwnerRepository;

    @Transactional
    public AuthRes signUp(final SignUpReq signUpReq) {
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
    public AuthRes signIn(final SignInReq signInReq) {
        User user = userRepository.findByEmail(signInReq.getEmail())
                .orElseThrow(InvalidUserException::new);
        if (!user.getProviderId().equals(signInReq.getProviderId())) {
            throw new InvalidPasswordException();
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInReq.getEmail(),
                        signInReq.getProviderId()
                )
        );

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

    @Transactional
    public AuthRes refresh(final RefreshTokenReq tokenRefreshRequest) {
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

    @Transactional
    public Message signOut(final RefreshTokenReq tokenRefreshRequest) {
        Token token = tokenRepository.findByRefreshToken(tokenRefreshRequest.getRefreshToken())
                .orElseThrow(() -> new DefaultAuthenticationException(ErrorCode.INVALID_AUTHENTICATION));
        tokenRepository.delete(token);

        return Message.builder()
                .message("로그아웃 하였습니다.")
                .build();
    }

    @Transactional
    public AuthRes shopOwnerSignUp(final ShopOwnerSignUpReq shopOwnerSignUpReq) {
        if (shopOwnerRepository.existsByPhoneNumber(shopOwnerSignUpReq.getPhoneNumber())) {
            throw new AlreadyExistPhoneNumberException();
        }

        ShopOwner shopOwner = ShopOwner.builder()
                .name(shopOwnerSignUpReq.getName())
                .phoneNumber(shopOwnerSignUpReq.getPhoneNumber())
                .password(passwordEncoder.encode(shopOwnerSignUpReq.getPassword()))
                .marketingConsent(shopOwnerSignUpReq.getMarketingConsent())
                .build();

        shopOwnerRepository.save(shopOwner);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        shopOwnerSignUpReq.getPhoneNumber(),
                        shopOwnerSignUpReq.getPassword()
                )
        );

        TokenMapping tokenMapping = customTokenProviderService.createToken(authentication);

        Token token = Token.builder()
                .refreshToken(tokenMapping.getRefreshToken())
                .userEmail(tokenMapping.getUserEmail())
                .build();
        tokenRepository.save(token);

        AuthRes authRes = AuthRes.builder()
                .accessToken(tokenMapping.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();

        return authRes;
    }

    @Transactional
    public AuthRes shopOwnerSignIn(final ShopOwnerSignInReq shopOwnerSignInReq) {
        ShopOwner shopOwner = shopOwnerRepository.findShopOwnerByPhoneNumber(shopOwnerSignInReq.getPhoneNumber())
                .orElseThrow(InvalidPhoneNumberException::new);

        if (!passwordEncoder.matches(shopOwnerSignInReq.getPassword(), shopOwner.getPassword())) {
            throw new InvalidPasswordException();
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        shopOwnerSignInReq.getPhoneNumber(),
                        shopOwnerSignInReq.getPassword()
                )
        );

        TokenMapping tokenMapping = customTokenProviderService.createToken(authentication);

        Token token = Token.builder()
                .refreshToken(tokenMapping.getRefreshToken())
                .userEmail(tokenMapping.getUserEmail())
                .build();
        tokenRepository.save(token);

        AuthRes authRes = AuthRes.builder()
                .accessToken(tokenMapping.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();

        return authRes;
    }

    @Transactional
    public Message shopOwnerChangePassword(final ShopOwnerChangePasswordReq shopOwnerChangePasswordReq) {
        ShopOwner shopOwner = shopOwnerRepository.findShopOwnerByPhoneNumber(shopOwnerChangePasswordReq.getPhoneNumber())
                .orElseThrow(InvalidShopOwnerException::new);

        shopOwner.changePassword(
                passwordEncoder.encode(shopOwnerChangePasswordReq.getChangePassword()));

        return Message.builder()
                .message("비밀번호가 변경되었습니다.").build();
    }

    private boolean valid(final String refreshToken) {
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