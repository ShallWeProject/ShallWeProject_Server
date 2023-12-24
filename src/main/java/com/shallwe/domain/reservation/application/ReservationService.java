package com.shallwe.domain.reservation.application;

import com.shallwe.domain.reservation.dto.DeleteReservationRes;
import com.shallwe.domain.reservation.dto.ReservationIdOwnerRes;
import com.shallwe.domain.reservation.dto.ReservationIdUserRes;
import com.shallwe.domain.reservation.dto.ReservationRequest;
import com.shallwe.domain.reservation.dto.ReservationResponse;
import com.shallwe.domain.reservation.dto.ReservationUserReq;
import com.shallwe.domain.reservation.dto.UpdateReservationReq;
import com.shallwe.domain.reservation.dto.ValidTimeSlotRes;
import com.shallwe.global.config.security.token.UserPrincipal;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

    List<ReservationIdOwnerRes> getReservationByDateOwner(UserPrincipal userPrincipal, Long giftId, LocalDate date);

    List<ReservationIdUserRes> getReservationByDateUser(UserPrincipal userPrincipal, Long giftId, LocalDate date);

    List<ValidTimeSlotRes> getValidReservationTime(UserPrincipal userPrincipal, Long giftId);

    List<ReservationResponse> addOwnerReservation(ReservationRequest reservationRequest, UserPrincipal userPrincipal);

    ReservationResponse addUserReservation(ReservationUserReq reservationRequest, UserPrincipal userPrincipal);

    List<ReservationResponse> findUserReservation(UserPrincipal userPrincipal);

    List<ReservationResponse> getAllReservation();

    List<ReservationResponse> getCurrentGiftReservation(Long giftId);

    ReservationResponse updateReservation(UpdateReservationReq updateReq, UserPrincipal userPrincipal);

    DeleteReservationRes deleteReservation(Long id);

}

