package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import lombok.Builder;
import lombok.Data;

@Data
public class ReservationIdUserRes {

  Long reservationId;
  ReservationStatus status;

  @Builder
  public ReservationIdUserRes(Long reservationId, ReservationStatus status) {
    this.reservationId = reservationId;
    this.status = status;
  }

  public static ReservationIdUserRes toDtoUser(Reservation reservation){
    return ReservationIdUserRes.builder()
        .reservationId(reservation.getId())
        .status(reservation.getReservationStatus())
        .build();
  }
}
