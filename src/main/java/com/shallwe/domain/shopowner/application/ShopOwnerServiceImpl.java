package com.shallwe.domain.shopowner.application;


import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.shopowner.domain.repository.ShopOwnerRepository;
import com.shallwe.domain.shopowner.dto.ShopOwnerChangePasswordReq;
import com.shallwe.domain.shopowner.exception.InvalidShopOwnerException;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ShopOwnerServiceImpl implements ShopOwnerService {

    private final PasswordEncoder passwordEncoder;
    private final ShopOwnerRepository shopOwnerRepository;

    @Transactional
    public Message shopOwnerChangePassword(final UserPrincipal userPrincipal, final ShopOwnerChangePasswordReq shopOwnerChangePasswordReq) {
        ShopOwner shopOwner = shopOwnerRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidShopOwnerException::new);

        shopOwner.changePassword(passwordEncoder.encode(shopOwnerChangePasswordReq.getChangePassword()));

        return Message.builder()
                .message("비밀번호가 변경되었습니다.").build();
    }

}
