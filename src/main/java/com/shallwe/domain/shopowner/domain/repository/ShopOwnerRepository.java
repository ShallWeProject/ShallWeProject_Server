package com.shallwe.domain.shopowner.domain.repository;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOwnerRepository extends JpaRepository<ShopOwner, Long> {

  List<ExperienceGift> findAllById(Long id);
}
