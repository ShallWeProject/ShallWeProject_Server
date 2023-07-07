package com.shallwe.domain.user.application;

import com.shallwe.domain.auth.domain.Token;
import com.shallwe.domain.auth.domain.repository.TokenRepository;
import com.shallwe.domain.common.Status;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.domain.repository.UserRepository;

import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.error.DefaultException;
import com.shallwe.global.payload.ApiResponse;
import com.shallwe.global.payload.ErrorCode;
import com.shallwe.global.payload.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> getCurrentUser(UserPrincipal userPrincipal){
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK, "유저가 올바르지 않습니다."));

        ApiResponse apiResponse = ApiResponse.builder().check(true).information(user).build();

        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<?> deleteCurrentUser(UserPrincipal userPrincipal){
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK, "유저가 올바르지 않습니다"));

        Token token = tokenRepository.findByUserEmail(user.getEmail())
                .orElseThrow(() -> new DefaultException(ErrorCode.INVALID_AUTHENTICATION, "토큰이 유효하지 않습니다."));

        user.updateStatus(Status.DELETE);
        tokenRepository.delete(token);

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(Message.builder().message("회원 탈퇴하셨습니다.").build())
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}
