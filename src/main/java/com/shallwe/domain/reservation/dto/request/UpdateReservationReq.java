package com.shallwe.domain.reservation.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class UpdateReservationReq {

    @Schema(type = "long", example = "2", description = "예약 날짜")
    private Long reservationId;

    @Schema(type = "string", example = "2023-08-25", description = "예약 날짜")
    private LocalDate date;

    @Schema(type = "string", example = "13:00", description = "예약 시간")
    private LocalTime time;
}
