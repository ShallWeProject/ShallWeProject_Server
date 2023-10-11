package com.shallwe.domain.shopowner.domain.repository;

import com.shallwe.domain.shopowner.domain.ShopOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOwnerRepository extends JpaRepository<ShopOwner, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

}
