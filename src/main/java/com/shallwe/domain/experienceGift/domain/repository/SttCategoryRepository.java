package com.shallwe.domain.experienceGift.domain.repository;

import com.shallwe.domain.experienceGift.domain.SttCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SttCategoryRepository extends JpaRepository<SttCategory,Long> {
    Optional<SttCategory> findBySttCategoryId (Long sttCategoryId);
}
