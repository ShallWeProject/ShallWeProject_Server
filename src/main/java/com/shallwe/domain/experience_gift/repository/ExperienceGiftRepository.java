package com.shallwe.domain.experience_gift.repository;

import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceGiftRepository extends JpaRepository<ExperienceGift, Long>, ExperienceGiftQuerydslRepository{

    List<ExperienceGift> findByTitleContains(String title);
    Optional<ExperienceGift> findByExperienceGiftId(Long experienceGiftId);

}
