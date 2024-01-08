package com.shallwe.domain.reservation.application;

import static com.shallwe.domain.reservation.domain.ReservationStatus.WAITING;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.repository.ExperienceGiftRepository;
import com.shallwe.domain.experiencegift.exception.ExperienceGiftNotFoundException;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.reservation.dto.response.ReservationIdOwnerRes;
import com.shallwe.domain.reservation.dto.response.ReservationIdUserRes;
import com.shallwe.domain.reservation.dto.response.ReservationResponse;
import com.shallwe.domain.reservation.dto.response.ValidTimeSlotRes;
import com.shallwe.domain.reservation.exception.InvalidAvailableTimeException;
import com.shallwe.domain.reservation.exception.InvalidReservationException;
import com.shallwe.domain.reservation.exception.InvalidUserException;
import com.shallwe.domain.shopowner.domain.repository.ShopOwnerRepository;
import com.shallwe.domain.user.domain.repository.UserRepository;
import com.shallwe.global.config.security.token.UserPrincipal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationCheckServiceImpl implements ReservationCheckService{

  private final ReservationRepository reservationRepository;
  private final ExperienceGiftRepository experienceGiftRepository;
  private final ShopOwnerRepository shopOwnerRepository;
  private final UserRepository userRepository;

  public List<ValidTimeSlotRes> getValidReservationTime(UserPrincipal userPrincipal, Long giftId) {
    ExperienceGift experienceGift = experienceGiftRepository.findById(giftId)
        .orElseThrow(ExperienceGiftNotFoundException::new);

    List<Reservation> reservations = reservationRepository.findAllByExperienceGiftAndReservationStatus(
            experienceGift, WAITING)
        .orElseThrow(InvalidAvailableTimeException::new);
    System.out.println(Arrays.toString(reservations.toArray()));
    return reservations.stream().map(reservation -> ValidTimeSlotRes.builder()
        .reservationId(reservation.getId())
        .date(reservation.getDate())
        .time(reservation.getTime())
        .build()).toList();
  }

  public List<ReservationIdUserRes> getReservationByDateUser(UserPrincipal userPrincipal,
      Long giftId,
      LocalDate date) {
    ExperienceGift experienceGift = experienceGiftRepository.findById(giftId)
        .orElseThrow(ExperienceGiftNotFoundException::new);

    List<Reservation> reservations = reservationRepository.findAllByExperienceGiftAndDate(
            experienceGift, date)

        .orElseThrow(InvalidReservationException::new);

    return reservations.stream()
        .map(ReservationIdUserRes::toDtoUser)
        .collect(Collectors.toList());
  }

  public List<ReservationIdOwnerRes> getReservationByDateOwner(UserPrincipal userPrincipal,
      Long giftId,
      LocalDate date) {
    ExperienceGift experienceGift = experienceGiftRepository.findById(giftId)
        .orElseThrow(ExperienceGiftNotFoundException::new);

    List<Reservation> reservations = reservationRepository.findAllByExperienceGiftAndDate(
            experienceGift, date)
        .orElseThrow(InvalidReservationException::new);

    return reservations.stream()
        .map(ReservationIdOwnerRes::toDtoOwner)
        .collect(Collectors.toList());
  }

  public List<ReservationResponse> findUserReservation(UserPrincipal userPrincipal) {
    return reservationRepository.findAllBySenderId(userPrincipal.getId()).orElseThrow(
            InvalidUserException::new)
        .stream().map(ReservationResponse::toDtoUser).collect(Collectors.toList());
  }

  public List<ReservationResponse> getAllReservation() {
    return reservationRepository.findAll().stream().map(ReservationResponse::toDtoUser)
        .collect(Collectors.toList());
  }

  public List<ReservationResponse> getCurrentGiftReservation(Long giftId) {
    ExperienceGift experienceGift = experienceGiftRepository.findById(giftId)
        .orElseThrow(ExperienceGiftNotFoundException::new);
    List<Reservation> reservations = reservationRepository.findAllByExperienceGift(experienceGift)
        .orElseThrow(ExperienceGiftNotFoundException::new);
    return reservations.stream().map(ReservationResponse::toDtoUser)
        .collect(Collectors.toList());
  }

}
