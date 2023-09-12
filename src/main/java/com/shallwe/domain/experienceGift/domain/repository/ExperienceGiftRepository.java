package com.shallwe.domain.experienceGift.domain.repository;

import com.shallwe.domain.experienceGift.domain.ExperienceGift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceGiftRepository extends JpaRepository<ExperienceGift, Long>, ExperienceGiftQuerydslRepository{

    List<ExperienceGift> findByTitleContains(String title);
    Optional<ExperienceGift> findByExperienceGiftId(Long experienceGiftId);

}
