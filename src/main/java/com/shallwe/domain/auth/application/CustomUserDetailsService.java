package com.shallwe.domain.auth.application;

import java.util.Optional;

import com.shallwe.domain.common.Status;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.shopowner.domain.repository.ShopOwnerRepository;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.domain.repository.UserRepository;

import com.shallwe.global.error.DefaultAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ShopOwnerRepository shopOwnerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmailAndStatus(email, Status.ACTIVE);
        if (user.isPresent()) {
            return UserPrincipal.createUser(user.get());
        }

        Optional<ShopOwner> shopOwner = shopOwnerRepository.findShopOwnerByPhoneNumberAndStatus(email, Status.ACTIVE);
        if (shopOwner.isPresent()) {
            return UserPrincipal.createShopOwner(shopOwner.get());
        }

        throw new UsernameNotFoundException("유효하지 않는 유저이거나, 사장입니다.");
    }

    public UserDetails loadUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return UserPrincipal.createUser(user.get());
        }

        Optional<ShopOwner> shopOwner = shopOwnerRepository.findById(id);
        if (shopOwner.isPresent()) {
            return UserPrincipal.createShopOwner(shopOwner.get());
        }

        throw new UsernameNotFoundException("유효하지 않는 유저이거나, 사장입니다.");
    }

}
