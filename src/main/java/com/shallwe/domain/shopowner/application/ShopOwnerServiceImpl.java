package com.shallwe.domain.shopowner.application;


import com.shallwe.domain.auth.domain.Token;
import com.shallwe.domain.auth.domain.repository.TokenRepository;
import com.shallwe.domain.common.Status;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.shopowner.domain.repository.ShopOwnerRepository;
import com.shallwe.domain.shopowner.dto.ShopOwnerChangePasswordReq;
import com.shallwe.domain.shopowner.exception.InvalidShopOwnerException;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.exception.InvalidTokenException;
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
    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public Message shopOwnerChangePassword(final UserPrincipal userPrincipal, final ShopOwnerChangePasswordReq shopOwnerChangePasswordReq) {
        ShopOwner shopOwner = shopOwnerRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidShopOwnerException::new);

        shopOwner.changePassword(passwordEncoder.encode(shopOwnerChangePasswordReq.getChangePassword()));

        return Message.builder()
                .message("비밀번호가 변경되었습니다.").build();
    }

    @Override
    @Transactional
    public Message deleteCurrentShopOwner(UserPrincipal userPrincipal) {
        ShopOwner shopOwner = shopOwnerRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidShopOwnerException::new);
        Token token = tokenRepository.findByUserEmail(userPrincipal.getEmail())
                .orElseThrow(InvalidTokenException::new);

        shopOwner.updateStatus(Status.DELETE);
        tokenRepository.delete(token);

        return Message.builder()
                .message("사장 탈퇴가 완료되었습니다.")
                .build();
    }

}
