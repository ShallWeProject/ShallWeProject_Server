package com.shallwe.domain.experiencegift.domain.repository;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.ExperienceGiftImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceGiftImageRepository extends JpaRepository<ExperienceGiftImage,Long> {

    List<ExperienceGiftImage> findByExperienceGift(ExperienceGift experienceGift);
    void deleteByExperienceGift(ExperienceGift experienceGift);

}
