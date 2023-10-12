package com.shallwe.domain.shopowner.application;


import com.shallwe.domain.auth.dto.AuthRes;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.shopowner.domain.repository.ShopOwnerRepository;
import com.shallwe.domain.shopowner.dto.ShopOwnerChangePasswordReq;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ShopOwnerServiceImpl implements ShopOwnerService {

    private final ShopOwnerRepository shopOwnerRepository;

    public Message shopOwnerChangePassword(final UserPrincipal userPrincipal, final ShopOwnerChangePasswordReq shopOwnerChangePasswordReq) {
        ShopOwner shopOwner = userPrincipal.getShopOwner();

        shopOwner.changePassword(shopOwnerChangePasswordReq.getChangePassword());

        return Message.builder()
                .message("비밀번호가 변경되었습니다.").build();
    }

}
