package com.shallwe.domain.reservation.dto;

import static com.amazonaws.services.ec2.model.PrincipalType.User;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.user.domain.User;
import java.time.LocalTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationIdOwnerRes {

  Long reservationId;
  ReservationStatus status;
  String sender;
  LocalTime time;
  String phoneNum;
  Long person;

  @Builder
  public ReservationIdOwnerRes(Long reservationId, ReservationStatus status, String sender,
      LocalTime time, String phoneNum,Long person) {
    this.reservationId = reservationId;
    this.status = status;
    this.sender = sender;
    this.time = time;
    this.phoneNum = phoneNum;
    this.person = person;
  }

  public static ReservationIdOwnerRes toDtoOwner(Reservation reservation) {
    return ReservationIdOwnerRes.builder()
        .reservationId(reservation.getId())
        .status(reservation.getReservationStatus())
        .time(reservation.getTime())
        .sender(Optional.ofNullable(reservation.getSender()).map(
            com.shallwe.domain.user.domain.User::getName).orElse(null))
        .phoneNum(reservation.getPhoneNumber())
        .person(reservation.getPersons())
        .build();
  }
}
