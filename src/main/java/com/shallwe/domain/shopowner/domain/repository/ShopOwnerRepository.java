package com.shallwe.domain.shopowner.domain.repository;

import com.shallwe.domain.common.Status;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShopOwnerRepository extends JpaRepository<ShopOwner, Long> {

    boolean existsByPhoneNumberAndStatus(String phoneNumber, Status status);

    Optional<ShopOwner> findShopOwnerByPhoneNumberAndStatus(String phoneNumber, Status status);

}
