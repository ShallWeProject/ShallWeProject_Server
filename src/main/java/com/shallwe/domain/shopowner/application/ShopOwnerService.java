package com.shallwe.domain.shopowner.application;

import com.shallwe.domain.shopowner.dto.ShopOwnerChangePasswordReq;
import com.shallwe.domain.shopowner.dto.ShopOwnerGiftManageRes;
import com.shallwe.domain.user.dto.DeleteUserRes;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.Message;

public interface ShopOwnerService {

    Message shopOwnerChangePassword(UserPrincipal userPrincipal, ShopOwnerChangePasswordReq shopOwnerChangePasswordReq);
    Message deleteCurrentShopOwner(UserPrincipal userPrincipal);
    ShopOwnerGiftManageRes getShopOwnerReservation(UserPrincipal userPrincipal, Long giftId);
    Message confirmPayment(UserPrincipal userPrincipal, Long reservationId);
}

