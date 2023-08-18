package com.shallwe.domain.experience_gift.repository;

import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import com.shallwe.domain.experience_gift.domain.Explanation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExplanationRepository extends JpaRepository<Explanation,Long> {
List<Explanation> findByExperienceGift(ExperienceGift experienceGift);
}
