package com.shallwe.domain.reservation.application;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.dto.ReservationResponse;

public interface ReservationService {
    ReservationResponse getCurrentReservation(Reservation reservation);

}

