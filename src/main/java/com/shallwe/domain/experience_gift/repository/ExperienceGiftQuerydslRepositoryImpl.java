package com.shallwe.domain.experience_gift.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.shallwe.domain.experience_gift.domain.QExperienceGift.experienceGift;

@RequiredArgsConstructor
@Repository
public class ExperienceGiftQuerydslRepositoryImpl implements ExperienceGiftQuerydslRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<ExperienceGift> findByExpCategoryId(Long ExpCategoryId) {
        return queryFactory.selectFrom(experienceGift)
                .where(experienceGift.expCategory.ExpCategoryId.eq(ExpCategoryId))
                .fetch();
    }

    @Override
    public List<ExperienceGift> findBySttCategoryId(Long SttCategoryId) {
        return queryFactory.selectFrom(experienceGift)
                .where(experienceGift.sttCategory.SttCategoryId.eq(SttCategoryId))
                .fetch();
    }
}
