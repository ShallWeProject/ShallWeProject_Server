package com.shallwe.domain.experience_gift.repository;

import com.shallwe.domain.experience_gift.domain.SttCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface SttCategoryRepository extends JpaRepository<SttCategory,Long> {
    Optional<SttCategory> findBySttCategoryId (Long sttCategoryId);
}
