package com.shallwe.domain.reservation.application;

import com.shallwe.domain.reservation.dto.ReservationIdOwnerRes;
import com.shallwe.domain.reservation.dto.ReservationIdUserRes;
import com.shallwe.global.config.security.token.UserPrincipal;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService {


    List<ReservationIdOwnerRes> getReservationByDateOwner (UserPrincipal userPrincipal, Long giftId, LocalDate date);
    List<ReservationIdUserRes> getReservationByDateUser (UserPrincipal userPrincipal, Long giftId, LocalDate date);


}

