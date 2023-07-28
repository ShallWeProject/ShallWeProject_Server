package com.shallwe.domain.reservation.application;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.reservation.dto.ReservationRequest;
import com.shallwe.domain.reservation.dto.ReservationResponse;
import com.shallwe.domain.reservation.dto.UpdateReservationReq;
import com.shallwe.domain.reservation.exception.InvalidReservationException;
import com.shallwe.domain.reservation.exception.InvalidUserException;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.domain.repository.UserRepository;
import com.shallwe.global.config.security.token.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReservationServiceImpl {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
//    private final GiftRepository giftRepository;


    //id 로 reservation 가져오기
    private Reservation getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(InvalidReservationException::new);
        return reservation;
    }

    @Transactional
    public ReservationResponse addReservation(final ReservationRequest reservationRequest, final UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);
        Reservation reservation = ReservationRequest.toEntity(reservationRequest, user);
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponse.toDto(savedReservation);

    }

    public List<ReservationResponse> findUserReservation(Long userId) {
        //파라미터로 넘겨받는 것도 좋지만 현재 세션 유저 정보를 받아서 findAllByUserId 하는 것도 괜찮아 보입니다.
        List<ReservationResponse> reservationRes = new ArrayList<>();
        List<Reservation> userReservations = reservationRepository.findAllByUserId(userId);
        for (Reservation userreservation : userReservations) {
            ReservationResponse reservationResponse = new ReservationResponse();
            reservationResponse.setId(userreservation.getId());
            //reservationRes1.setGift_id(userreservation.getGift_id());
            reservationResponse.setPersons(userreservation.getPersons());
            reservationResponse.setDate(userreservation.getDate());
            reservationResponse.setSender(userreservation.getSender());
            reservationResponse.setPhone_number(userreservation.getPhone_number());
            reservationResponse.setInvitation_img(userreservation.getInvitation_img());
            reservationResponse.setInvitation_comment(userreservation.getInvitation_comment());
            reservationResponse.setReservation_status(userreservation.getReservation_status());
            reservationRes.add(reservationResponse);
        }
        return reservationRes;
    }

    public List<ReservationResponse> getAllReservation() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponse> reservationRes = new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationResponse reservationResponse1 = new ReservationResponse();
            reservationResponse1.setId(reservation.getId());
            //reservationRes1.setGift_id(reservation.getGift_id());
            reservationResponse1.setPersons(reservation.getPersons());
            reservationResponse1.setDate(reservation.getDate());
            reservationResponse1.setSender(reservation.getSender());
            reservationResponse1.setPhone_number(reservation.getPhone_number());
            reservationResponse1.setInvitation_img(reservation.getInvitation_img());
            reservationResponse1.setInvitation_comment(reservation.getInvitation_comment());
            reservationResponse1.setReservation_status(reservation.getReservation_status());
            reservationRes.add(reservationResponse1);
        }
        return reservationRes;
    }

    @Transactional
    public ReservationResponse updateReservation(UpdateReservationReq updateReq, UserPrincipal userPrincipal) {
        // 로그인한 사용자 ID와 예약 ID로 예약 찾기
        Optional<Reservation> foundReservation =
                reservationRepository.findByUserIdAndId(userPrincipal.getId(), updateReq.getId());
        // 예약을 찾은 후 업데이트 및 저장, 아니면 예외 처리
        Reservation updatedReservation = foundReservation.map(reservation -> {
            reservation.updateReservation(updateReq);
            return reservationRepository.save(reservation);
        }).orElseThrow(InvalidReservationException::new);
        // DTO로 변환 후 반환
        return ReservationResponse.toDto(updatedReservation);
    }


    @Transactional
    public Reservation cancelReservation(Long id) {
        Reservation reservation = getReservation(id);
        // reservation.cancelReservation();
        return reservation;
    }

    @Transactional
    public void deleteReservation(Long id) {
        Reservation reservation = getReservation(id);
        reservationRepository.delete(reservation);
    }


}
