package com.shallwe.domain.experienceGift.repository;

import com.shallwe.domain.experienceGift.domain.ExpCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ExpCategoryRepository extends JpaRepository<ExpCategory,Long> {
    Optional<ExpCategory> findByExpCategoryId (long expCategoryId);
}
