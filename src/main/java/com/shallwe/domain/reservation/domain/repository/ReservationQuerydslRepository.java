package com.shallwe.domain.reservation.domain.repository;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.user.domain.User;

import java.util.List;

public interface ReservationQuerydslRepository {

    List<Reservation> findReservationsByPhoneNumberAndReservationStatusIn(String phoneNumber);
    List<Reservation> findReservationsBySenderAndReservationStatusIn(User sender);

}
