package com.shallwe.domain.experiencegift.domain.repository;

import com.shallwe.domain.experiencegift.domain.ExperienceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ExperienceCategoryRepository extends JpaRepository<ExperienceCategory,Long> {

    Optional<ExperienceCategory> findByExpCategory(String expCategory);

}
