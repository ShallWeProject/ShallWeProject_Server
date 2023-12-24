package com.shallwe.domain.reservation.dto;


import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;
import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationIdOwnerRes {

    @Schema(description = "예약 Id")
    Long reservationId;

    @Schema(description = "예약 상태 {WAITING(예약대기), BOOKED(예약중), COMFIRMED(예약확정), COMPLETED(이용완료), CANCELED(예약취소)}")
    ReservationStatus status;

    @Schema(description = "보내는이 이름(예약자)")
    String sender;

    @Schema(description = "예약 시간")
    LocalTime time;

    @Schema(description = "전화번호")
    String phoneNum;

    @Schema(description = "예약 인원")
    Long person;

    @Builder
    public ReservationIdOwnerRes(Long reservationId, ReservationStatus status, String sender,
                                 LocalTime time, String phoneNum, Long person) {
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
