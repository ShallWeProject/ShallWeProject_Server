package com.shallwe.domain.reservation.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shallwe.domain.experiencegift.domain.QExpCategory;
import com.shallwe.domain.experiencegift.domain.QExperienceGift;
import com.shallwe.domain.experiencegift.domain.QSttCategory;
import com.shallwe.domain.experiencegift.domain.QSubtitle;
import com.shallwe.domain.reservation.domain.QReservation;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.user.domain.QUser;
import com.shallwe.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.shallwe.domain.experiencegift.domain.QExpCategory.*;
import static com.shallwe.domain.experiencegift.domain.QExperienceGift.*;
import static com.shallwe.domain.experiencegift.domain.QSttCategory.*;
import static com.shallwe.domain.experiencegift.domain.QSubtitle.*;
import static com.shallwe.domain.reservation.domain.QReservation.*;
import static com.shallwe.domain.user.domain.QUser.*;

@RequiredArgsConstructor
@Repository
public class ReservationQuerydslRepositoryImpl implements ReservationQuerydslRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Reservation> findReservationsByPhoneNumberAndReservationStatusIn(String phoneNumber) {
        return queryFactory
                .selectFrom(reservation)
                .leftJoin(reservation.experienceGift, experienceGift).fetchJoin()
                .leftJoin(reservation.experienceGift.subtitle, subtitle).fetchJoin()
                .leftJoin(reservation.experienceGift.expCategory, expCategory1).fetchJoin()
                .leftJoin(reservation.experienceGift.sttCategory, sttCategory1).fetchJoin()
                .leftJoin(reservation.sender, new QUser("sender")).fetchJoin()
                .where(
                        reservation.phoneNumber.eq(phoneNumber)
                                .and(reservation.reservationStatus.in(ReservationStatus.BOOKED, ReservationStatus.COMPLETED))
                )
                .orderBy(reservation.reservationStatus.asc()).orderBy(reservation.date.desc())
                .fetch();
    }

    @Override
    public List<Reservation> findReservationsBySenderAndReservationStatusIn(User sender) {
        return queryFactory
                .selectFrom(reservation)
                .leftJoin(reservation.experienceGift, experienceGift).fetchJoin()
                .leftJoin(reservation.experienceGift.subtitle, subtitle).fetchJoin()
                .leftJoin(reservation.experienceGift.expCategory, expCategory1).fetchJoin()
                .leftJoin(reservation.experienceGift.sttCategory, sttCategory1).fetchJoin()
                .leftJoin(reservation.receiver, new QUser("receiver")).fetchJoin()
                .where(
                        reservation.sender.eq(sender)
                                .and(reservation.reservationStatus.in(ReservationStatus.BOOKED, ReservationStatus.COMPLETED))
                )
                .orderBy(reservation.reservationStatus.asc()).orderBy(reservation.date.desc())
                .fetch();
    }

}
