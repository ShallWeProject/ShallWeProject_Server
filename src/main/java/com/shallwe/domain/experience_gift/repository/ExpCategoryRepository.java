package com.shallwe.domain.experience_gift.repository;

import com.shallwe.domain.experience_gift.domain.ExpCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ExpCategoryRepository extends JpaRepository<ExpCategory,Long> {
    Optional<ExpCategory> findByExpCategoryId (long expCategoryId);
}
