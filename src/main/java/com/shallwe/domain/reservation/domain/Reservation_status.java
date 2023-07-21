package com.shallwe.domain.reservation.domain;

import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Reservation_status {
    CANCELLED, BOOKED, COMPLETED
}
