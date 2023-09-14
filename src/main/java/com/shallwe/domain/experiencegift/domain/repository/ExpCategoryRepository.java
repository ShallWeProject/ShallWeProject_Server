package com.shallwe.domain.experiencegift.domain.repository;

import com.shallwe.domain.experiencegift.domain.ExpCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ExpCategoryRepository extends JpaRepository<ExpCategory,Long> {
    Optional<ExpCategory> findByExpCategoryId (long expCategoryId);
}