package com.shallwe.domain.experiencegift.domain.repository;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.ExperienceGiftImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceGiftImgRepository extends JpaRepository<ExperienceGiftImg,Long> {
    List<ExperienceGiftImg> findByExperienceGift(ExperienceGift experienceGift);

    void deleteByExperienceGift(ExperienceGift experienceGift);
}
