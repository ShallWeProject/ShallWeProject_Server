package com.shallwe.domain.user.application;

import com.shallwe.domain.auth.domain.Token;
import com.shallwe.domain.auth.domain.repository.TokenRepository;
import com.shallwe.domain.common.Status;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.domain.repository.UserRepository;

import com.shallwe.domain.user.dto.UserDeleteRes;
import com.shallwe.domain.user.dto.UserRes;
import com.shallwe.domain.user.exception.InvalidUserException;
import com.shallwe.domain.user.exception.InvalidTokenException;
import com.shallwe.global.config.security.token.UserPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserRes getCurrentUser(UserPrincipal userPrincipal){
//        User user = userRepository.findById(userPrincipal.getId())
//                .orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK, "유저가 올바르지 않습니다."));

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);
        return UserRes.toDto(user);

//        ApiResponse apiResponse = ApiResponse.builder().check(true).information(user).build();
//
//        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public UserDeleteRes deleteCurrentUser(UserPrincipal userPrincipal){
//        User user = userRepository.findById(userPrincipal.getId())
//                .orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK, "유저가 올바르지 않습니다"));
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);

        Token token = tokenRepository.findByUserEmail(user.getEmail())
                .orElseThrow(InvalidTokenException::new);
        user.updateStatus(Status.DELETE);
        tokenRepository.delete(token);

        return UserDeleteRes.toDto();

//        ApiResponse apiResponse = ApiResponse.builder()
//                .check(true)
//                .information(Message.builder().message("회원 탈퇴하셨습니다.").build())
//                .build();

//        return ResponseEntity.ok(apiResponse);
    }

}
