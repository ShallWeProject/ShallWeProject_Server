package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.*;

import static com.shallwe.domain.reservation.domain.ReservationStatus.BOOKED;
import static com.shallwe.domain.reservation.domain.ReservationStatus.WAITING;


@Data
@NoArgsConstructor
public class ReservationRequest {

  private Long experienceGiftId;
  private Long persons;
  private Long ownerId;
  private Map<LocalDate, List<LocalTime>> dateTimeMap;
  private String phoneNumber;
  private String imageKey;
  private String invitationComment;
  private ReservationStatus reservationStatus;

  public static List<Reservation> toEntityForUser(final ReservationRequest reservationRequest,
      ExperienceGift experienceGift, ShopOwner owner) {
    List<Reservation> reservations = new ArrayList<>();

    for (Map.Entry<LocalDate, List<LocalTime>> entry : reservationRequest.getDateTimeMap()
        .entrySet()) {
      LocalDate date = entry.getKey();
      List<LocalTime> times = entry.getValue();

      for (LocalTime time : times) {
        Reservation toEntity = Reservation.builder()
            .experienceGift(experienceGift)
            .persons(reservationRequest.getPersons())
            .date(date)
            .time(time)
            .owner(owner)
            .phoneNumber(reservationRequest.getPhoneNumber())
            .invitationImg(reservationRequest.getImageKey())
            .invitationComment(reservationRequest.getInvitationComment())
            .reservationStatus(BOOKED)
            .build();

        reservations.add(toEntity);
      }
    }
    return reservations;
  }

  public static List<Reservation> toEntityForOwner(ReservationRequest reservationRequest,
      ExperienceGift experienceGift,ShopOwner owner) {
    List<Reservation> reservations = new ArrayList<>();

    for (Map.Entry<LocalDate, List<LocalTime>> entry : reservationRequest.getDateTimeMap()
        .entrySet()) {
      LocalDate date = entry.getKey();
      List<LocalTime> times = entry.getValue();

      for (LocalTime time : times) {
        Reservation toEntity = Reservation.builder()
            .experienceGift(experienceGift)
            .date(date)
            .time(time)
            .owner(owner)
            .reservationStatus(WAITING)
            .build();

        reservations.add(toEntity);
      }
    }

    return reservations;
  }

}

