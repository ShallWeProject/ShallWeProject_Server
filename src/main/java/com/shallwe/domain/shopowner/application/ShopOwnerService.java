package com.shallwe.domain.shopowner.application;


import com.shallwe.domain.reservation.dto.ReservationResponse;



import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.Message;
import java.util.List;

public interface ShopOwnerService {

    Message deleteCurrentShopOwner(UserPrincipal userPrincipal);
    List<ReservationResponse> getShopOwnerReservation(UserPrincipal userPrincipal, Long giftId);
    Message confirmPayment(UserPrincipal userPrincipal, Long reservationId);

}

