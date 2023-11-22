package com.shallwe.domain.shopowner.application;

import com.shallwe.domain.shopowner.dto.ShopOwnerGiftManageRes;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.Message;

public interface ShopOwnerService {

    Message deleteCurrentShopOwner(UserPrincipal userPrincipal);
    ShopOwnerGiftManageRes getShopOwnerReservation(UserPrincipal userPrincipal, Long giftId);
    Message confirmPayment(UserPrincipal userPrincipal, Long reservationId);

}

