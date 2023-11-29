package com.shallwe.domain.reservation.application;

import com.shallwe.domain.reservation.dto.ReservationResponse;
import com.shallwe.global.config.security.token.UserPrincipal;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

    List<ReservationResponse> getReservationByDate (UserPrincipal userPrincipal, Long giftId, LocalDate date);

}

