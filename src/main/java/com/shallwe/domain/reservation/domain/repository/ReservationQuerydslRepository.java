package com.shallwe.domain.reservation.domain.repository;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.dto.ReceiveGiftDetailRes;
import com.shallwe.domain.user.dto.SendGiftDetailRes;

import java.time.LocalDate;
import java.util.List;

public interface ReservationQuerydslRepository {

    List<ReceiveGiftDetailRes> findReservationsByPhoneNumberAndReservationStatusIn(String phoneNumber);

    List<SendGiftDetailRes> findReservationsBySenderAndReservationStatusIn(User sender);

    List<Reservation> findReservationsByDateAndUser(LocalDate date, User user);

}
