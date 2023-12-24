package com.shallwe.domain.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidTimeSlotRes {

    private Long reservationId;
    private LocalDate date;
    private LocalTime time;

}
