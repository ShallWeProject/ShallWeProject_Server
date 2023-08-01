package com.shallwe.domain.experience_gift.repository;

import com.shallwe.domain.experience_gift.domain.ExperienceGift;

import java.util.List;

public interface ExperienceGiftQuerydslRepository {
    List<ExperienceGift> findByExpCategoryId(Long ExpCategoryId);
    List<ExperienceGift> findBySttCategoryId(Long SttCategoryId);
}
