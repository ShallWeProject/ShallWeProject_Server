package com.shallwe.domain.reservation.application;

import com.shallwe.domain.reservation.dto.response.ReservationIdOwnerRes;
import com.shallwe.domain.reservation.dto.response.ReservationIdUserRes;
import com.shallwe.domain.reservation.dto.response.ReservationResponse;
import com.shallwe.domain.reservation.dto.response.ValidTimeSlotRes;
import com.shallwe.global.config.security.token.UserPrincipal;
import java.time.LocalDate;
import java.util.List;

public interface ReservationCheckService {

  List<ReservationIdOwnerRes> getReservationByDateOwner (UserPrincipal userPrincipal, Long giftId, LocalDate date);

  List<ReservationIdUserRes> getReservationByDateUser (UserPrincipal userPrincipal, Long giftId, LocalDate date);

  public List<ValidTimeSlotRes> getValidReservationTime(UserPrincipal userPrincipal, Long giftId);

  public List<ReservationResponse> findUserReservation(UserPrincipal userPrincipal);

  public List<ReservationResponse> getAllReservation();

  public List<ReservationResponse> getCurrentGiftReservation(Long giftId);
}
