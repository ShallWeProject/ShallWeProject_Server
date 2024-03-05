package com.shallwe.domain.reservation.application;

import static com.shallwe.domain.reservation.domain.ReservationStatus.*;
import static com.shallwe.domain.reservation.domain.ReservationStatus.WAITING;

import com.shallwe.domain.common.Status;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.repository.ExperienceGiftRepository;
import com.shallwe.domain.experiencegift.exception.ExperienceGiftNotFoundException;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.reservation.dto.request.OwnerReservationCreate;
import com.shallwe.domain.reservation.dto.response.ReservationResponse;
import com.shallwe.domain.reservation.dto.request.UserReservationCreate;
import com.shallwe.domain.reservation.dto.request.UpdateReservationReq;
import com.shallwe.domain.reservation.exception.*;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.domain.repository.UserRepository;
import com.shallwe.domain.user.exception.InvalidUserException;
import com.shallwe.global.config.security.token.UserPrincipal;

import java.util.List;
import java.util.stream.Collectors;

import com.shallwe.global.infrastructure.sms.NaverSmsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationManipulationServiceImpl implements ReservationManipulationService {

    private final ReservationRepository reservationRepository;
    private final ExperienceGiftRepository experienceGiftRepository;
    private final UserRepository userRepository;
    private final NaverSmsClient naverSmsClient;

    @Override
    @Transactional
    public List<ReservationResponse> addOwnerReservation(OwnerReservationCreate ownerReservationCreate, UserPrincipal userPrincipal) {
        ExperienceGift experienceGift = experienceGiftRepository.findById(ownerReservationCreate.getExperienceGiftId())
                .orElseThrow(ExperienceGiftNotFoundException::new);

        List<Reservation> reservations = OwnerReservationCreate.toEntityForOwner(ownerReservationCreate, experienceGift);
        return reservations.stream()
                .map(reservationRepository::save)
                .map(ReservationResponse::toDtoOwner)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservationResponse addUserReservation(UserReservationCreate reservationRequest, UserPrincipal userPrincipal) throws Exception {
        User sender = userRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidSenderException::new);

        User receiver = userRepository.findByPhoneNumberAndStatus(reservationRequest.getPhoneNumber(), Status.ACTIVE)
                .orElseThrow(InvalidReceiverException::new);

        ExperienceGift experienceGift = experienceGiftRepository.findExperienceGiftById(reservationRequest.getExperienceGiftId())
                .orElseThrow(ExperienceGiftNotFoundException::new);

        Reservation reservation = reservationRepository.findByDateAndTimeAndExperienceGift(
                        reservationRequest.getDate(), reservationRequest.getTime(), experienceGift)
                .orElseThrow(InvalidReservationException::new);

        if (reservation.getReservationStatus().equals(WAITING)) {
            reservation.updateStatus(BOOKED);
            reservation.updateUserReservationRequest(reservationRequest, sender, receiver);
            naverSmsClient.sendApply(receiver, experienceGift, reservation);
            experienceGift.addReservationCount();
        } else {
            throw new InvalidReservationException();
        }
        return ReservationResponse.toDtoUser(reservation);
    }

    @Override
    @Transactional
    public ReservationResponse updateReservation(UpdateReservationReq updateReq, UserPrincipal userPrincipal) {
        Reservation updateReservation = reservationRepository.findById(
                updateReq.getReservationId()).map(
                reservation -> {
                    reservation.updateReservation(updateReq);
                    return reservationRepository.save(reservation);
                }
        ).orElseThrow(InvalidReservationException::new);
        return ReservationResponse.toDtoUser(updateReservation);
    }

    @Override
    @Transactional
    public void cancelReservation(final UserPrincipal userPrincipal, final Long reservationId) throws Exception {
        Reservation reservation = reservationRepository.findReservationById(reservationId)
                .orElseThrow(InvalidReservationException::new);

        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidUserException::new);

        if (!reservation.getSender().getId().equals(user.getId()))
            throw new UserReservationMismatchException();

        if(reservation.getReservationStatus().equals(BOOKED)) { // BOOKED 상태일 때 WAITING으로 변경
            reservation.updateStatus(WAITING);
        } else if(reservation.getReservationStatus().equals(CONFIRMED)) { // CONFIRMED 상태일 때 CANCELLED로 변경
            reservation.updateStatus(CANCELLED);
        } else { // 예약 상태가 BOOKED나 CONFIRMED가 아닐 경우
            throw new NotAvailableReservationStatusException();
        }

        naverSmsClient.sendCancel(reservation);
    }

}
