package com.shallwe.domain.user.application;

import com.shallwe.domain.auth.domain.OAuth2Token;
import com.shallwe.domain.auth.domain.RefreshToken;
import com.shallwe.domain.auth.domain.repository.OAuth2TokenRepository;
import com.shallwe.domain.auth.domain.repository.RefreshTokenRepository;
import com.shallwe.domain.auth.exception.InvalidOAuth2RefreshTokenException;
import com.shallwe.domain.common.Status;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.user.domain.Complain;
import com.shallwe.domain.user.domain.Provider;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.domain.repository.ComplainRepository;
import com.shallwe.domain.user.domain.repository.UserRepository;

import com.shallwe.domain.user.dto.*;
import com.shallwe.domain.user.exception.InvalidUserException;
import com.shallwe.domain.user.exception.InvalidTokenException;
import com.shallwe.global.config.security.AuthConfig;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.utils.AppleJwtUtils;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ReservationRepository reservationRepository;
    private final ComplainRepository complainRepository;
    private final OAuth2TokenRepository oAuth2TokenRepository;
    private final AppleJwtUtils appleJwtUtils;
    private final AuthConfig authConfig;

    @Override
    public UserDetailRes getCurrentUser(final UserPrincipal userPrincipal) {
        return UserDetailRes.toDto(userPrincipal.getUser());
    }

    @Override
    @Transactional
    public void inactiveCurrentUser(final UserPrincipal userPrincipal, final PostComplainReq postComplainReq) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidUserException::new);

        Complain complain = Complain.builder()
                .content(postComplainReq.getComplain())
                .build();

        if(user.getProvider().equals(Provider.APPLE)) {
            OAuth2Token oAuth2RefreshToken = oAuth2TokenRepository.findByProviderId(user.getProviderId())
                    .orElseThrow(InvalidOAuth2RefreshTokenException::new);

            appleJwtUtils.revokeToken(oAuth2RefreshToken.getRefreshToken());
        }

        if (user.getProvider().equals(Provider.GOOGLE)) {
            OAuth2Token oAuth2RefreshToken = oAuth2TokenRepository.findByProviderId(user.getProviderId())
                    .orElseThrow(InvalidOAuth2RefreshTokenException::new);

            RestClient restClient = RestClient.builder()
                    .baseUrl("https://oauth2.googleapis.com")
                    .requestFactory(new HttpComponentsClientHttpRequestFactory())
                    .build();

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("token", oAuth2RefreshToken.getRefreshToken());

            restClient.post()
                    .uri("/revoke")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();
        }

        if (user.getProvider().equals(Provider.KAKAO)) {
            RestClient restClient = RestClient.builder()
                    .baseUrl("https://kapi.kakao.com")
                    .requestFactory(new HttpComponentsClientHttpRequestFactory())
                    .build();

            Map<String, String> body = Map.of("target_id_type", "user_id", "target_id", user.getProviderId());

            restClient.post()
                    .uri("/v1/user/unlink")
                    .header("Authorization", "KakaoAK " + authConfig.getAuth().getKakaoAdminKey())
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();
        }

        RefreshToken refreshToken = refreshTokenRepository.findByProviderId(user.getEmail())
                .orElseThrow(InvalidTokenException::new);

        user.updateStatus(Status.DELETE);
        refreshTokenRepository.delete(refreshToken);
        complainRepository.save(complain);
    }

    @Override
    @Transactional
    public void signUpCurrentUser(final UserPrincipal userPrincipal, final SignUpUserReq signUpUserReq) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);

        user.updateName(signUpUserReq.getName());
        user.updatePhoneNumber(signUpUserReq.getPhoneNumber());
        user.updateMarketingConsent(signUpUserReq.getMarketingConsent());
        user.updateAge(signUpUserReq.getAge());
        user.updateGender(signUpUserReq.getGender());
    }

    @Override
    public List<SendGiftDetailRes> findSendGiftsByUser(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);

        return reservationRepository
                .findReservationsBySenderAndReservationStatusIn(user);
    }

    @Override
    public List<ReceiveGiftDetailRes> findReceiveGiftsByUser(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);

        return reservationRepository
                .findReservationsByPhoneNumberAndReservationStatusIn(user.getPhoneNumber());
    }

}