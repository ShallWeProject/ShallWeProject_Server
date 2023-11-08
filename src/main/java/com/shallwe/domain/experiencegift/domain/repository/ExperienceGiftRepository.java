package com.shallwe.domain.experiencegift.domain.repository;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceGiftRepository extends JpaRepository<ExperienceGift, Long>, ExperienceGiftQuerydslRepository{

    List<ExperienceGift> findByTitleContains(String title);
    Optional<ExperienceGift> findByExperienceGiftId(Long experienceGiftId);

    List<ExperienceGift> findByShopOwnerId(Long id);
}
