package com.shallwe.domain.user.application;

import com.shallwe.domain.auth.domain.Token;
import com.shallwe.domain.auth.domain.repository.TokenRepository;
import com.shallwe.domain.common.Status;
import com.shallwe.domain.user.domain.Gender;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.domain.repository.UserRepository;

import com.shallwe.domain.user.dto.DeleteUserRes;
import com.shallwe.domain.user.dto.SignUpUserReq;
import com.shallwe.domain.user.dto.SignUpUserRes;
import com.shallwe.domain.user.dto.UserDetailRes;
import com.shallwe.domain.user.exception.InvalidUserException;
import com.shallwe.domain.user.exception.InvalidTokenException;
import com.shallwe.global.config.security.token.UserPrincipal;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Override
    public UserDetailRes getCurrentUser(final UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);
        return UserDetailRes.toDto(user);
    }

    @Override
    @Transactional
    public DeleteUserRes deleteCurrentUser(final UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);

        Token token = tokenRepository.findByUserEmail(user.getEmail())
                .orElseThrow(InvalidTokenException::new);
        user.updateStatus(Status.DELETE);
        tokenRepository.delete(token);

        return DeleteUserRes.toDto();
    }

    @Override
    @Transactional
    public SignUpUserRes signUpCurrentUser(final UserPrincipal userPrincipal, final SignUpUserReq signUpUserReq) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);

        user.updateMarketingConsent(signUpUserReq.getMarketingConsent());
        user.updateAge(signUpUserReq.getAge());
        user.updateGender(signUpUserReq.getGender());

        return SignUpUserRes.toDto();
    }

}
