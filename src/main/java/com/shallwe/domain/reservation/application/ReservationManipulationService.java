package com.shallwe.domain.reservation.application;

import com.shallwe.domain.reservation.dto.DeleteReservationRes;
import com.shallwe.domain.reservation.dto.ReservationRequest;
import com.shallwe.domain.reservation.dto.ReservationResponse;
import com.shallwe.domain.reservation.dto.ReservationUserReq;
import com.shallwe.domain.reservation.dto.UpdateReservationReq;
import com.shallwe.global.config.security.token.UserPrincipal;
import java.util.List;

public interface ReservationManipulationService {

  public List<ReservationResponse> addOwnerReservation(ReservationRequest reservationRequest,
      UserPrincipal userPrincipal);

  public ReservationResponse addUserReservation(ReservationUserReq reservationRequest,
      UserPrincipal userPrincipal);

  public ReservationResponse updateReservation(UpdateReservationReq updateReq,
      UserPrincipal userPrincipal);

  public DeleteReservationRes deleteReservation(Long id);

}
