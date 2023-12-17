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

    List<ReservationIdOwnerRes> getReservationByDateOwner (UserPrincipal userPrincipal, Long giftId, LocalDate date);
    List<ReservationIdUserRes> getReservationByDateUser (UserPrincipal userPrincipal, Long giftId, LocalDate date);

    public List<ValidTimeSlotRes> getValidReservationTime(UserPrincipal userPrincipal, Long giftId);

    public List<ReservationResponse> addOwnerReservation(ReservationRequest reservationRequest,
        UserPrincipal userPrincipal);

    public ReservationResponse addUserReservation(ReservationUserReq reservationRequest,
        UserPrincipal userPrincipal);

    public List<ReservationResponse> findUserReservation(UserPrincipal userPrincipal);

    public List<ReservationResponse> getAllReservation();

    public List<ReservationResponse> getCurrentGiftReservation(Long giftId);

    public ReservationResponse updateReservation(UpdateReservationReq updateReq,
        UserPrincipal userPrincipal);

    public DeleteReservationRes deleteReservation(Long id);
    }

