package com.shallwe.domain.reservation.application;

import com.shallwe.domain.experience_gift.exception.ExperienceGiftNotFoundException;
import com.shallwe.domain.experience_gift.repository.ExperienceGiftRepository;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.reservation.dto.DeleteReservationRes;
import com.shallwe.domain.reservation.dto.ReservationRequest;
import com.shallwe.domain.reservation.dto.ReservationResponse;
import com.shallwe.domain.reservation.dto.UpdateReservationReq;
import com.shallwe.domain.reservation.exception.InvalidReservationException;
import com.shallwe.domain.reservation.exception.InvalidUserException;
import com.shallwe.domain.user.domain.repository.UserRepository;
import com.shallwe.domain.user.exception.InvalidPhoneNumberException;
import com.shallwe.global.config.security.token.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReservationServiceImpl {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ExperienceGiftRepository experienceGiftRepository;

    private ReservationResponse getReservation(Long id) {
        return reservationRepository.findById(id).map(ReservationResponse::toDto).orElseThrow(InvalidReservationException::new);
    }

    @Transactional
    public ReservationResponse addReservation( ReservationRequest reservationRequest,  UserPrincipal userPrincipal) {
        Reservation reservation = ReservationRequest.toEntity(
                reservationRequest,
                userRepository.findById(userPrincipal.getId())
                        .orElseThrow(InvalidUserException::new),
                userRepository.findByPhoneNumber(reservationRequest.getPhoneNumber())
                        .orElseThrow(InvalidPhoneNumberException::new),
                experienceGiftRepository.findByExperienceGiftId(reservationRequest.getExperienceGiftId())
                        .orElseThrow(ExperienceGiftNotFoundException::new));

        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponse.toDto(savedReservation);
    }

    public List<ReservationResponse> findUserReservation(UserPrincipal userPrincipal) {
        return reservationRepository.findAllBySenderId(userPrincipal.getId())
                .stream().map(ReservationResponse::toDto).collect(Collectors.toList());
    }

    public List<ReservationResponse> getAllReservation() {
        return reservationRepository.findAll().stream().map(ReservationResponse::toDto).collect(Collectors.toList());
    }
    //추가
    public List<ReservationResponse> getCurrentGiftReservation(Long giftId){
        experienceGiftRepository.findById(giftId).orElseThrow(ExperienceGiftNotFoundException::new);
        return reservationRepository.findByExperienceGift_Id(giftId).stream().map(ReservationResponse::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ReservationResponse updateReservation(UpdateReservationReq updateReq, UserPrincipal userPrincipal) {
        Reservation updateReservation= reservationRepository.findBySenderIdAndId(userPrincipal.getId(),updateReq.getId()).map(
                reservation -> {reservation.updateReservation(updateReq);
                return reservationRepository.save(reservation);}
        ).orElseThrow(InvalidReservationException::new);
        return ReservationResponse.toDto(updateReservation);
    }

    @Transactional
    public DeleteReservationRes deleteReservation(UserPrincipal userPrincipal, Long id) {
        Optional<Reservation> foundReservation = reservationRepository.findBySenderIdAndId(userPrincipal.getId(), id);
        foundReservation.ifPresentOrElse(reservationRepository::delete,
                () -> {throw new InvalidReservationException();});
        return DeleteReservationRes.toDTO();
    }

}
