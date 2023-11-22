package com.shallwe.domain.user.application;

import com.shallwe.domain.auth.domain.Token;
import com.shallwe.domain.auth.domain.repository.TokenRepository;
import com.shallwe.domain.common.Status;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.domain.repository.UserRepository;

import com.shallwe.domain.user.dto.*;
import com.shallwe.domain.user.exception.InvalidUserException;
import com.shallwe.domain.user.exception.InvalidTokenException;
import com.shallwe.global.config.security.token.UserPrincipal;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public UserDetailRes getCurrentUser(final UserPrincipal userPrincipal) {
        return UserDetailRes.toDto(userPrincipal.getUser());
    }

    @Override
    @Transactional
    public DeleteUserRes inactiveCurrentUser(final UserPrincipal userPrincipal) {
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

        user.updatePhoneNumber(signUpUserReq.getPhoneNumber());
        user.updateMarketingConsent(signUpUserReq.getMarketingConsent());
        user.updateAge(signUpUserReq.getAge());
        user.updateGender(signUpUserReq.getGender());

        return SignUpUserRes.toDto();
    }

    @Override
    public List<SendGiftDetailRes> findSendGiftsByUser(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);

        List<Reservation> reservations = reservationRepository
                .findReservationBySenderAndReservationStatusIn(user, Arrays.asList(ReservationStatus.BOOKED, ReservationStatus.COMPLETED));

        return reservations.stream()
                .map(SendGiftDetailRes::toDto)
                .toList();
    }

    @Override
    public List<ReceiveGiftDetailRes> findReceiveGiftsByUser(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);

        List<Reservation> reservations = reservationRepository
                .findReservationByPhoneNumberAndReservationStatusIn(user.getPhoneNumber(), Arrays.asList(ReservationStatus.BOOKED, ReservationStatus.COMPLETED));

        return reservations.stream()
                .map(ReceiveGiftDetailRes::toDto)
                .toList();
    }

}
