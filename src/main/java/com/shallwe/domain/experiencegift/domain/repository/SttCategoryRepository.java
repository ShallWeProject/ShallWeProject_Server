package com.shallwe.domain.experiencegift.domain.repository;

import com.shallwe.domain.experiencegift.domain.SttCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SttCategoryRepository extends JpaRepository<SttCategory,Long> {
    Optional<SttCategory> findBySttCategoryId (Long sttCategoryId);

    Optional<SttCategory> findBySttCategory(String sttCategory);
}
