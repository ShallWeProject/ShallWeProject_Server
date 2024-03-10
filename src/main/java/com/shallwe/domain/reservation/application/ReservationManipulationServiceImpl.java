package com.shallwe.domain.reservation.application;

import static com.shallwe.domain.reservation.domain.ReservationStatus.*;

import static com.shallwe.domain.reservation.domain.ReservationStatus.WAITING;

import com.shallwe.domain.common.Status;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.repository.ExperienceGiftRepository;
import com.shallwe.domain.experiencegift.exception.ExperienceGiftNotFoundException;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.reservation.dto.request.OwnerReservationCreate;
import com.shallwe.domain.reservation.dto.response.ReservationResponse;
import com.shallwe.domain.reservation.dto.request.UserReservationCreate;
import com.shallwe.domain.reservation.dto.request.UpdateReservationReq;
import com.shallwe.domain.reservation.exception.InvalidReservationException;
import com.shallwe.domain.reservation.exception.InvalidReservationUpdateException;
import com.shallwe.domain.reservation.exception.InvalidSenderException;
import com.shallwe.domain.reservation.exception.InvalidReceiverException;
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
    public List<ReservationResponse> addOwnerReservation(
            OwnerReservationCreate ownerReservationCreate, UserPrincipal userPrincipal) {
        ExperienceGift experienceGift = experienceGiftRepository.findById(
                        ownerReservationCreate.getExperienceGiftId())
                .orElseThrow(ExperienceGiftNotFoundException::new);

        List<Reservation> reservations = OwnerReservationCreate.toEntityForOwner(ownerReservationCreate,
                experienceGift);
        return reservations.stream()
                .map(reservationRepository::save)
                .map(ReservationResponse::toDtoOwner)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservationResponse addUserReservation(UserReservationCreate reservationRequest,
                                                  UserPrincipal userPrincipal) throws Exception {
        User sender = userRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidSenderException::new);

        User receiver = userRepository.findByPhoneNumberAndStatus(reservationRequest.getPhoneNumber(),
                        Status.ACTIVE)
                .orElseThrow(InvalidReceiverException::new);

        ExperienceGift experienceGift = experienceGiftRepository.findExperienceGiftById(
                        reservationRequest.getExperienceGiftId())
                .orElseThrow(ExperienceGiftNotFoundException::new);

        Reservation reservation = reservationRepository.findByDateAndTimeAndExperienceGiftWithPessimisticLock(
                        reservationRequest.getDate(), reservationRequest.getTime(), experienceGift)
                .orElseThrow(InvalidReservationException::new);

        if (reservation.getReservationStatus().equals(WAITING)) {
            reservation.updateStatus(BOOKED);
            reservation.updateUserReservationRequest(reservationRequest, sender, receiver);
            naverSmsClient.sendApply(sender, receiver, experienceGift, reservation);
            experienceGift.addReservationCount();
        } else {
            throw new InvalidReservationException();
        }
        return ReservationResponse.toDtoUser(reservation);
    }

    @Override
    @Transactional
    public ReservationResponse updateReservation(UpdateReservationReq updateReq, UserPrincipal userPrincipal) throws Exception {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidUserException::new);
        //현재 예약 검색
        Reservation currentReservation = reservationRepository.findReservationById(updateReq.getReservationId())
                .orElseThrow(InvalidReservationException::new);

        if(!currentReservation.getSender().getId().equals(user.getId()))
            throw new UserReservationMismatchException();

        //변경 가능한 예약 검색
        Reservation newReservation = reservationRepository.findByDateAndTimeAndExperienceGift(
                updateReq.getDate(), updateReq.getTime(),
                currentReservation.getExperienceGift()
        ).orElseThrow(InvalidReservationUpdateException::new);

        //DTO 생성
        UserReservationCreate reservationCreateDto = UserReservationCreate.builder()
                .persons(currentReservation.getPersons())
                .experienceGiftId(currentReservation.getExperienceGift().getId())
                .invitationComment(currentReservation.getInvitationComment())
                .phoneNumber(currentReservation.getPhoneNumber())
                .imageUrl(currentReservation.getInvitationImg())
                .build();

        //기존 예약 정보를 현재 예약 정보로 이동
        newReservation.updateUserReservationRequest(reservationCreateDto,
                currentReservation.getSender(), currentReservation.getReceiver());
        newReservation.updateStatus(BOOKED);
        Reservation updated = reservationRepository.save(newReservation);

        //기존 예약 정보 상태 초기화
        currentReservation.clearReservation();
        currentReservation.updateStatus(WAITING);
        reservationRepository.save(currentReservation);

        naverSmsClient.sendChange(newReservation);
        return ReservationResponse.toDtoUser(updated);
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

        if (reservation.getReservationStatus().equals(BOOKED)) { // BOOKED 상태일 때 WAITING으로 변경
            reservation.updateStatus(WAITING);
            naverSmsClient.sendCancel(reservation);
            reservation.clearReservation();
        } else if (reservation.getReservationStatus().equals(CONFIRMED)) { // CONFIRMED 상태일 때 CANCELLED로 변경
            reservation.updateStatus(CANCELLED);
            naverSmsClient.sendCancel(reservation);
        } else { // 예약 상태가 BOOKED나 CONFIRMED가 아닐 경우
            throw new NotAvailableReservationStatusException();
        }
    }

}
