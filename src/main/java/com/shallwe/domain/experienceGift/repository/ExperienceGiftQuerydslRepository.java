package com.shallwe.domain.experienceGift.repository;

import com.shallwe.domain.experienceGift.domain.ExperienceGift;

import java.util.List;

public interface ExperienceGiftQuerydslRepository {

    List<ExperienceGift> findGiftsBySttCategoryIdOrderByPriceDesc(Long SttCategoryId);
    List<ExperienceGift> findGiftsBySttCategoryIdOrderByPriceAsc(Long SttCategoryId);
    List<ExperienceGift> findGiftsByExpCategoryIdOrderByPriceDesc(Long ExpCategoryId);
    List<ExperienceGift> findGiftsByExpCategoryIdOrderByPriceAsc(Long ExpCategoryId);
    List<ExperienceGift> findPopularGiftsBySttCategoryId(Long sttCategoryId);
    List<ExperienceGift> findPopularGiftsByExpCategoryId(Long ExpCategoryId);
    List<ExperienceGift> findAllPopularGifts();
}
