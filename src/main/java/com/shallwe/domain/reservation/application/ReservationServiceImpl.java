package com.shallwe.domain.reservation.application;

import com.shallwe.domain.common.Status;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.exception.ExperienceGiftNotFoundException;
import com.shallwe.domain.experiencegift.domain.repository.ExperienceGiftRepository;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.reservation.dto.DeleteReservationRes;
import com.shallwe.domain.reservation.dto.ReservationRequest;
import com.shallwe.domain.reservation.dto.ReservationResponse;
import com.shallwe.domain.reservation.dto.ReservationUserReq;
import com.shallwe.domain.reservation.dto.UpdateReservationReq;
import com.shallwe.domain.reservation.exception.InvalidReservationException;
import com.shallwe.domain.reservation.exception.InvalidUserException;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.shopowner.domain.repository.ShopOwnerRepository;
import com.shallwe.domain.shopowner.exception.InvalidShopOwnerException;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.domain.repository.UserRepository;
import com.shallwe.global.config.security.token.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReservationServiceImpl {

  private final ReservationRepository reservationRepository;
  private final ExperienceGiftRepository experienceGiftRepository;
  private final ShopOwnerRepository shopOwnerRepository;
  private final UserRepository userRepository;


  @Transactional
  public List<ReservationResponse> addOwnerReservation(ReservationRequest reservationRequest,
      UserPrincipal userPrincipal) {
    ExperienceGift experienceGift = experienceGiftRepository.findByExperienceGiftId(
            reservationRequest.getExperienceGiftId())
        .orElseThrow(ExperienceGiftNotFoundException::new);

    ShopOwner owner = shopOwnerRepository.findById(reservationRequest.getOwnerId())
        .orElseThrow(InvalidShopOwnerException::new);

    List<Reservation> reservations = ReservationRequest.toEntityForOwner(reservationRequest,
        experienceGift, owner);
    return reservations.stream()
        .map(reservationRepository::save)
        .map(ReservationResponse::toDtoOwner)
        .collect(Collectors.toList());
  }

  @Transactional
  public ReservationResponse addUserReservation(ReservationUserReq reservationRequest,
      UserPrincipal userPrincipal) {

    User nonPersistentSender = userPrincipal.getUser();
    User sender = userRepository.findById(nonPersistentSender.getId()).orElseThrow(
        InvalidUserException::new);
    User receiver = userRepository.findByPhoneNumber(reservationRequest.getPhoneNumber()).orElseThrow(
        InvalidUserException::new);

    Reservation reservation = reservationRepository.findByDateAndTime(
        reservationRequest.getDate(), reservationRequest.getTime())
        .orElseThrow(InvalidReservationException::new);

    if(reservation.getReservationStatus().equals(ReservationStatus.WAITING)){
      reservation.updateStatus(ReservationStatus.BOOKED);
      reservation.updateUserReservationRequest(reservationRequest,sender,receiver);
    }else{
      throw new InvalidReservationException();
    }
    return  ReservationResponse.toDtoUser(reservation);
  }

  public List<ReservationResponse> findUserReservation(UserPrincipal userPrincipal) {
    return reservationRepository.findAllBySenderId(userPrincipal.getId())
        .stream().map(ReservationResponse::toDtoUser).collect(Collectors.toList());
  }

  public List<ReservationResponse> getAllReservation() {
    return reservationRepository.findAll().stream().map(ReservationResponse::toDtoUser)
        .collect(Collectors.toList());
  }

  public List<ReservationResponse> getCurrentGiftReservation(Long giftId) {
    experienceGiftRepository.findById(giftId).orElseThrow(ExperienceGiftNotFoundException::new);
    return reservationRepository.findByExperienceGift_Id(giftId).stream()
        .map(ReservationResponse::toDtoUser).collect(Collectors.toList());
  }

  @Transactional
  public ReservationResponse updateReservation(UpdateReservationReq updateReq,
      UserPrincipal userPrincipal) {

    Reservation updateReservation = reservationRepository.findById(
        updateReq.getId()).map(
        reservation -> {
          reservation.updateReservation(updateReq);
          return reservationRepository.save(reservation);
        }
    ).orElseThrow(InvalidReservationException::new);
    return ReservationResponse.toDtoUser(updateReservation);
  }

  @Transactional
  public DeleteReservationRes deleteReservation(Long id) {
    Reservation reservation = reservationRepository.findById(id)
        .orElseThrow(InvalidReservationException::new);
    reservation.updateStatus(Status.DELETE);
    return DeleteReservationRes.toDTO();
  }
}
