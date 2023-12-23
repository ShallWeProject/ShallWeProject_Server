package com.shallwe.domain.experiencegift.domain.repository;

import com.shallwe.domain.experiencegift.domain.SituationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SituationCategoryRepository extends JpaRepository<SituationCategory,Long> {

    Optional<SituationCategory> findBySttCategory(String sttCategory);

}
