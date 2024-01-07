package com.shallwe.domain.shopowner.application;


import com.shallwe.domain.reservation.dto.response.ValidTimeSlotRes;
import com.shallwe.domain.shopowner.dto.ShopOwnerIdentificationReq;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.Message;
import java.util.List;

public interface ShopOwnerService {

    Message deleteCurrentShopOwner(UserPrincipal userPrincipal);
    List<ValidTimeSlotRes> getShopOwnerReservation(UserPrincipal userPrincipal, Long giftId);
    Message confirmPayment(UserPrincipal userPrincipal, Long reservationId);
    Message uploadShopOwnerIdentification(ShopOwnerIdentificationReq shopOwnerIdentificationReq, UserPrincipal userPrincipal);

}

