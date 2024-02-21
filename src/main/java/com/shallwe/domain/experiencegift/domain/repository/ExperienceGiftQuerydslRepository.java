package com.shallwe.domain.experiencegift.domain.repository;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.dto.response.ExperienceGiftRes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ExperienceGiftQuerydslRepository {

    List<ExperienceGift> findGiftsBySttCategoryIdOrderByPriceDesc(Long SttCategoryId);
    List<ExperienceGift> findGiftsBySttCategoryIdOrderByPriceAsc(Long SttCategoryId);
    List<ExperienceGift> findGiftsByExpCategoryIdOrderByPriceDesc(Long ExpCategoryId);
    List<ExperienceGift> findGiftsByExpCategoryIdOrderByPriceAsc(Long ExpCategoryId);
    List<ExperienceGift> findPopularGiftsBySttCategoryId(Long sttCategoryId);
    List<ExperienceGift> findPopularGiftsByExpCategoryId(Long ExpCategoryId);
    List<ExperienceGift> findAllPopularGifts();
    Slice<ExperienceGiftRes> findPagedExperienceGifts(Pageable pageable, String sttCategory, String expCategory, String sortCondition);

}
