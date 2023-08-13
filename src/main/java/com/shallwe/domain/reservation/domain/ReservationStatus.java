package com.shallwe.domain.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationStatus {
    CANCELLED, BOOKED, COMPLETED
}
