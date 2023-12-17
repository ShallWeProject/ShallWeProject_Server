package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class ReservationIdUserRes {

  @Schema(description = "예약 Id")
  Long reservationId;

  @Schema(description = "예약 상태 {WAITING(예약대기), BOOKED(예약중), COMFIRMED(예약확정), COMPLETED(이용완료), CANCELED(예약취소)}")
  ReservationStatus status;

  @Schema(description = "가능한 시간")
  String time;

  @Builder
  public ReservationIdUserRes(Long reservationId, ReservationStatus status,String time) {
    this.reservationId = reservationId;
    this.status = status;
    this.time = time;

  }

  public static ReservationIdUserRes toDtoUser(Reservation reservation){
    return ReservationIdUserRes.builder()
        .reservationId(reservation.getId())
        .time(reservation.getTime().toString())
        .status(reservation.getReservationStatus())
        .build();
  }
}
