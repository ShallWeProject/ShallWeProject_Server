package com.shallwe.domain.experiencegift.domain.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shallwe.domain.common.Status;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.QExperienceCategory;
import com.shallwe.domain.experiencegift.domain.QExperienceGiftExperienceCategory;
import com.shallwe.domain.experiencegift.domain.QSituationCategory;
import com.shallwe.domain.experiencegift.dto.response.ExperienceGiftRes;
import com.shallwe.domain.experiencegift.dto.response.QExperienceGiftRes;
import com.shallwe.domain.reservation.domain.QReservation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.shallwe.domain.experiencegift.domain.QExperienceCategory.*;
import static com.shallwe.domain.experiencegift.domain.QExperienceGift.experienceGift;
import static com.shallwe.domain.experiencegift.domain.QExperienceGiftExperienceCategory.*;
import static com.shallwe.domain.experiencegift.domain.QExperienceGiftSituationCategory.*;
import static com.shallwe.domain.experiencegift.domain.QSituationCategory.*;
import static com.shallwe.global.config.Constant.ExperienceGiftConstant.*;


@RequiredArgsConstructor
@Repository
public class ExperienceGiftQuerydslRepositoryImpl implements ExperienceGiftQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ExperienceGift> findGiftsBySttCategoryIdOrderByPriceDesc(Long SttCategoryId) {
        return queryFactory.selectFrom(experienceGift)
                .where(experienceGift.situationCategory.id.eq(SttCategoryId)
                        .and(experienceGift.status.eq(Status.ACTIVE)))
                .orderBy(experienceGift.price.desc())
                .fetch();
    }

    @Override
    public List<ExperienceGift> findGiftsBySttCategoryIdOrderByPriceAsc(Long SttCategoryId) {
        return queryFactory.selectFrom(experienceGift)
                .where(experienceGift.situationCategory.id.eq(SttCategoryId)
                        .and(experienceGift.status.eq(Status.ACTIVE)))
                .orderBy(experienceGift.price.asc())
                .fetch();
    }

    @Override
    public List<ExperienceGift> findGiftsByExpCategoryIdOrderByPriceDesc(Long ExpCategoryId) {
        return queryFactory.selectFrom(experienceGift)
                .where(experienceGift.experienceCategory.id.eq(ExpCategoryId)
                        .and(experienceGift.status.eq(Status.ACTIVE)))
                .orderBy(experienceGift.price.desc())
                .fetch();
    }

    @Override
    public List<ExperienceGift> findGiftsByExpCategoryIdOrderByPriceAsc(Long ExpCategoryId) {
        return queryFactory.selectFrom(experienceGift)
                .where(experienceGift.experienceCategory.id.eq(ExpCategoryId)
                        .and(experienceGift.status.eq(Status.ACTIVE)))
                .orderBy(experienceGift.price.asc())
                .fetch();
    }

    @Override
    public List<ExperienceGift> findPopularGiftsBySttCategoryId(Long sttCategoryId) {
        QReservation reservation = QReservation.reservation;

        return queryFactory.selectFrom(experienceGift)
                .leftJoin(reservation).on(experienceGift.id.eq(reservation.experienceGift.id))
                .where(experienceGift.situationCategory.id.eq(sttCategoryId)
                        .and(experienceGift.status.eq(Status.ACTIVE)))
                .groupBy(experienceGift.id)
                .orderBy(reservation.id.count().desc())
                .fetch();
    }

    @Override
    public List<ExperienceGift> findPopularGiftsByExpCategoryId(Long ExpCategoryId) {
        QReservation reservation = QReservation.reservation;

        return queryFactory.selectFrom(experienceGift)
                .leftJoin(reservation).on(experienceGift.id.eq(reservation.experienceGift.id))
                .where(experienceGift.experienceCategory.id.eq(ExpCategoryId)
                        .and(experienceGift.status.eq(Status.ACTIVE)))
                .groupBy(experienceGift.id)
                .orderBy(reservation.id.count().desc())
                .fetch();
    }

    @Override
    public List<ExperienceGift> findAllPopularGifts() {
        QReservation reservation = QReservation.reservation;

        return queryFactory.selectFrom(experienceGift)
                .leftJoin(reservation).on(experienceGift.id.eq(reservation.experienceGift.id))
                .where(experienceGift.status.eq(Status.ACTIVE))
                .groupBy(experienceGift.id)
                .orderBy(reservation.id.count().desc())
                .fetch();
    }

    @Override
    public Slice<ExperienceGiftRes> findPagedExperienceGifts(final Pageable pageable, final String sttCategory, final String searchCondition, final String expCategory, final String sortCondition) {
        List<ExperienceGiftRes> results = queryFactory
                .select(new QExperienceGiftRes(
                        experienceGift.id,
                        experienceGift.giftImgUrl,
                        experienceGift.subtitle.title,
                        experienceGift.title,
                        experienceGift.price
                ))
                .distinct()
                .from(experienceGift)
                .leftJoin(experienceGift.subtitle)
                .leftJoin(experienceGiftExperienceCategory).on(experienceGiftExperienceCategory.experienceGift.id.eq(experienceGift.id))
                .leftJoin(experienceGiftSituationCategory).on(experienceGiftSituationCategory.experienceGift.id.eq(experienceGift.id))
                .where(
                        experienceGift.status.eq(Status.ACTIVE),
                        sttCategoryEq(sttCategory),
                        expCategoryEq(expCategory),
                        searchConditionEq(searchCondition)
                )
                .orderBy(orderBy(sortCondition)) // WAITING이 아닌 예약의 개수를 기준으로 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // +1 해서 다음 페이지가 있는지 체크
                .fetch();

        boolean hasNext = results.size() > pageable.getPageSize();
        List<ExperienceGiftRes> content = hasNext ? results.subList(0, pageable.getPageSize()) : results;

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression searchConditionEq(String searchCondition) {
        if (searchCondition == null) {
            return null;
        }
        return experienceGift.title.contains(searchCondition).or(experienceGift.subtitle.title.contains(searchCondition));
    }

    private BooleanExpression sttCategoryEq(String sttCategory) {
        if (sttCategory == null) {
            return null;
        }
        return experienceGiftSituationCategory.situationCategory.sttCategory.eq(sttCategory);
    }

    private BooleanExpression expCategoryEq(String expCategory) {
        if (expCategory == null) {
            return null;
        }
        return experienceGiftExperienceCategory.experienceCategory.expCategory.eq(expCategory);
    }

    private OrderSpecifier<?> orderBy(String condition) {
        if (condition.equals(POPULAR_EXPERIENCE_GIFT)) { // 인기순
            return experienceGift.reservationCount.desc().nullsLast();
        }
        if (condition.equals(HIGH_PRICED_ORDER)) { // 가격 높은 순
            return experienceGift.price.desc();
        }
        if (condition.equals(LOW_PRICED_ORDER)) { // 가격 낮은 순
            return experienceGift.price.asc();
        }
        return experienceGift.reservationCount.desc().nullsLast(); // Default 인기순
    }

}