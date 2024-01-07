package com.shallwe.domain.reservation.application;

import com.shallwe.domain.reservation.dto.response.DeleteReservationRes;
import com.shallwe.domain.reservation.dto.request.OwnerReservationCreate;
import com.shallwe.domain.reservation.dto.response.ReservationResponse;
import com.shallwe.domain.reservation.dto.request.UserReservationCreate;
import com.shallwe.domain.reservation.dto.request.UpdateReservationReq;
import com.shallwe.global.config.security.token.UserPrincipal;
import java.util.List;

public interface ReservationManipulationService {

  public List<ReservationResponse> addOwnerReservation(
      OwnerReservationCreate ownerReservationCreate,
      UserPrincipal userPrincipal);

  public ReservationResponse addUserReservation(UserReservationCreate reservationRequest,
      UserPrincipal userPrincipal);

  public ReservationResponse updateReservation(UpdateReservationReq updateReq,
      UserPrincipal userPrincipal);

  public DeleteReservationRes deleteReservation(Long id);

}
