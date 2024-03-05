package com.shallwe.domain.reservation.application;

import com.shallwe.domain.reservation.dto.request.OwnerReservationCreate;
import com.shallwe.domain.reservation.dto.response.ReservationResponse;
import com.shallwe.domain.reservation.dto.request.UserReservationCreate;
import com.shallwe.domain.reservation.dto.request.UpdateReservationReq;
import com.shallwe.global.config.security.token.UserPrincipal;

import java.util.List;

public interface ReservationManipulationService {

    List<ReservationResponse> addOwnerReservation(OwnerReservationCreate ownerReservationCreate, UserPrincipal userPrincipal);
    ReservationResponse addUserReservation(UserReservationCreate reservationRequest, UserPrincipal userPrincipal) throws Exception;
    ReservationResponse updateReservation(UpdateReservationReq updateReq, UserPrincipal userPrincipal) throws Exception;
    void cancelReservation(UserPrincipal userPrincipal, Long reservationId) throws Exception;

}
