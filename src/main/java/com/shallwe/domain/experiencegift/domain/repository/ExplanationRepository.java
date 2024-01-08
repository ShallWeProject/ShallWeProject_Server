package com.shallwe.domain.experiencegift.domain.repository;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.Explanation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExplanationRepository extends JpaRepository<Explanation, Long> {

    List<Explanation> findByExperienceGift(ExperienceGift experienceGift);
    void deleteByExperienceGift(ExperienceGift experienceGift);

}
