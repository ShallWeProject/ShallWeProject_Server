package com.shallwe.domain.experiencegift.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shallwe.domain.common.Status;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.reservation.domain.QReservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.shallwe.domain.experiencegift.domain.QExperienceGift.experienceGift;

//import com.shallwe.domain.experiencegift.domain.QExperienceGift.ExperienceGift;


@RequiredArgsConstructor
@Repository
public class ExperienceGiftQuerydslRepositoryImpl implements ExperienceGiftQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ExperienceGift> findGiftsBySttCategoryIdOrderByPriceDesc(Long SttCategoryId) {
        return queryFactory.selectFrom(experienceGift)
                .where(experienceGift.sttCategory.sttCategoryId.eq(SttCategoryId)
                        .and(experienceGift.status.eq(Status.ACTIVE)))
                .orderBy(experienceGift.price.desc())
                .fetch();
    }

    @Override
    public List<ExperienceGift> findGiftsBySttCategoryIdOrderByPriceAsc(Long SttCategoryId) {
        return queryFactory.selectFrom(experienceGift)
                .where(experienceGift.sttCategory.sttCategoryId.eq(SttCategoryId)
                        .and(experienceGift.status.eq(Status.ACTIVE)))
                .orderBy(experienceGift.price.asc())
                .fetch();
    }

    @Override
    public List<ExperienceGift> findGiftsByExpCategoryIdOrderByPriceDesc(Long ExpCategoryId) {
        return queryFactory.selectFrom(experienceGift)
                .where(experienceGift.expCategory.expCategoryId.eq(ExpCategoryId)
                        .and(experienceGift.status.eq(Status.ACTIVE)))
                .orderBy(experienceGift.price.desc())
                .fetch();
    }

    @Override
    public List<ExperienceGift> findGiftsByExpCategoryIdOrderByPriceAsc(Long ExpCategoryId) {
        return queryFactory.selectFrom(experienceGift)
                .where(experienceGift.expCategory.expCategoryId.eq(ExpCategoryId)
                        .and(experienceGift.status.eq(Status.ACTIVE)))
                .orderBy(experienceGift.price.asc())
                .fetch();
    }

    @Override
    public List<ExperienceGift> findPopularGiftsBySttCategoryId(Long sttCategoryId) {
        QReservation reservation = QReservation.reservation;

        return queryFactory.selectFrom(experienceGift)
                .leftJoin(reservation).on(experienceGift.experienceGiftId.eq(reservation.experienceGift.experienceGiftId))
                .where(experienceGift.sttCategory.sttCategoryId.eq(sttCategoryId)
                        .and(experienceGift.status.eq(Status.ACTIVE)))
                .groupBy(experienceGift.experienceGiftId)
                .orderBy(reservation.id.count().desc())
                .fetch();
    }

    @Override
    public List<ExperienceGift> findPopularGiftsByExpCategoryId(Long ExpCategoryId) {
        QReservation reservation = QReservation.reservation;

        return queryFactory.selectFrom(experienceGift)
                .leftJoin(reservation).on(experienceGift.experienceGiftId.eq(reservation.experienceGift.experienceGiftId))
                .where(experienceGift.expCategory.expCategoryId.eq(ExpCategoryId)
                        .and(experienceGift.status.eq(Status.ACTIVE)))
                .groupBy(experienceGift.experienceGiftId)
                .orderBy(reservation.id.count().desc())
                .fetch();
    }

    @Override
    public List<ExperienceGift> findAllPopularGifts() {
        QReservation reservation = QReservation.reservation;

        return queryFactory.selectFrom(experienceGift)
                .leftJoin(reservation).on(experienceGift.experienceGiftId.eq(reservation.experienceGift.experienceGiftId))
                .where(experienceGift.status.eq(Status.ACTIVE))
                .groupBy(experienceGift.experienceGiftId)
                .orderBy(reservation.id.count().desc())
                .fetch();
    }


}
