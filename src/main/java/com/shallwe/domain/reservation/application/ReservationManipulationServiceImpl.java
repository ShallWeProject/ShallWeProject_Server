package com.shallwe.domain.reservation.application;

import static com.shallwe.domain.reservation.domain.ReservationStatus.WAITING;

import com.shallwe.domain.common.Status;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.repository.ExperienceGiftRepository;
import com.shallwe.domain.experiencegift.exception.ExperienceGiftNotFoundException;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.reservation.dto.response.DeleteReservationRes;
import com.shallwe.domain.reservation.dto.request.OwnerReservationCreate;
import com.shallwe.domain.reservation.dto.response.ReservationResponse;
import com.shallwe.domain.reservation.dto.request.UserReservationCreate;
import com.shallwe.domain.reservation.dto.request.UpdateReservationReq;
import com.shallwe.domain.reservation.exception.InvalidReservationException;
import com.shallwe.domain.reservation.exception.InvalidUserException;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.shopowner.domain.repository.ShopOwnerRepository;
import com.shallwe.domain.shopowner.exception.InvalidShopOwnerException;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.domain.repository.UserRepository;
import com.shallwe.global.config.security.token.UserPrincipal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationManipulationServiceImpl implements ReservationManipulationService{

  private final ReservationRepository reservationRepository;
  private final ExperienceGiftRepository experienceGiftRepository;
  private final ShopOwnerRepository shopOwnerRepository;
  private final UserRepository userRepository;

  @Transactional
  public List<ReservationResponse> addOwnerReservation(
      OwnerReservationCreate ownerReservationCreate,
      UserPrincipal userPrincipal) {
    ExperienceGift experienceGift = experienceGiftRepository.findById(
            ownerReservationCreate.getExperienceGiftId())
        .orElseThrow(ExperienceGiftNotFoundException::new);

    ShopOwner owner = shopOwnerRepository.findById(userPrincipal.getShopOwner().getId())
        .orElseThrow(InvalidShopOwnerException::new);

    List<Reservation> reservations = OwnerReservationCreate.toEntityForOwner(ownerReservationCreate,
        experienceGift, owner);
    return reservations.stream()
        .map(reservationRepository::save)
        .map(ReservationResponse::toDtoOwner)
        .collect(Collectors.toList());
  }

  @Transactional
  public ReservationResponse addUserReservation(UserReservationCreate reservationRequest,
      UserPrincipal userPrincipal) {

    User nonPersistentSender = userPrincipal.getUser();
    User sender = userRepository.findById(nonPersistentSender.getId()).orElseThrow(
        InvalidUserException::new);
    User receiver = userRepository.findByPhoneNumber(reservationRequest.getPhoneNumber())
        .orElseThrow(
            InvalidUserException::new);

    Reservation reservation = reservationRepository.findByDateAndTime(
            reservationRequest.getDate(), reservationRequest.getTime())
        .orElseThrow(InvalidReservationException::new);

    if (reservation.getReservationStatus().equals(WAITING)) {
      reservation.updateStatus(ReservationStatus.BOOKED);
      reservation.updateUserReservationRequest(reservationRequest, sender, receiver);
    } else {
      throw new InvalidReservationException();
    }
    return ReservationResponse.toDtoUser(reservation);
  }

  @Transactional
  public ReservationResponse updateReservation(UpdateReservationReq updateReq,
      UserPrincipal userPrincipal) {

    Reservation updateReservation = reservationRepository.findById(
        updateReq.getReservationId()).map(
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
