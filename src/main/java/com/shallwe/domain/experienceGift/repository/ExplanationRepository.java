package com.shallwe.domain.experienceGift.repository;

import com.shallwe.domain.experienceGift.domain.ExperienceGift;
import com.shallwe.domain.experienceGift.domain.Explanation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExplanationRepository extends JpaRepository<Explanation,Long> {
List<Explanation> findByExperienceGift(ExperienceGift experienceGift);
}
