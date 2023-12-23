package com.shallwe.domain.reservation.domain.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.user.domain.QUser;
import com.shallwe.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.shallwe.domain.experiencegift.domain.QExperienceCategory.*;
import static com.shallwe.domain.experiencegift.domain.QExperienceGift.*;
import static com.shallwe.domain.experiencegift.domain.QSituationCategory.*;
import static com.shallwe.domain.experiencegift.domain.QSubtitle.*;
import static com.shallwe.domain.memoryphoto.domain.QMemoryPhoto.memoryPhoto;
import static com.shallwe.domain.reservation.domain.QReservation.*;

@RequiredArgsConstructor
@Repository
public class ReservationQuerydslRepositoryImpl implements ReservationQuerydslRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Reservation> findReservationsByPhoneNumberAndReservationStatusIn(String phoneNumber) {
        return queryFactory
                .selectFrom(reservation)
                .leftJoin(reservation.experienceGift, experienceGift).fetchJoin()
                .leftJoin(reservation.experienceGift.subtitle, subtitle).fetchJoin()
                .leftJoin(reservation.experienceGift.experienceCategory, experienceCategory).fetchJoin()
                .leftJoin(reservation.experienceGift.situationCategory, situationCategory).fetchJoin()
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
                .leftJoin(reservation.experienceGift.experienceCategory, experienceCategory).fetchJoin()
                .leftJoin(reservation.experienceGift.situationCategory, situationCategory).fetchJoin()
                .leftJoin(reservation.receiver, new QUser("receiver")).fetchJoin()
                .where(
                        reservation.sender.eq(sender)
                                .and(reservation.reservationStatus.in(ReservationStatus.BOOKED, ReservationStatus.COMPLETED))
                )
                .orderBy(reservation.reservationStatus.asc()).orderBy(reservation.date.desc())
                .fetch();
    }

    @Override
    public List<Reservation> findReservationsByDateAndUser(LocalDate date, User user) {
        return queryFactory
                .selectFrom(reservation)
                .leftJoin(reservation.experienceGift, experienceGift).fetchJoin()
                .leftJoin(reservation.experienceGift.subtitle, subtitle).fetchJoin()
                .leftJoin(reservation.memoryPhotos, memoryPhoto).fetchJoin()
                .leftJoin(memoryPhoto.uploader).fetchJoin()
                .leftJoin(reservation.sender).fetchJoin()
                .leftJoin(reservation.receiver).fetchJoin()
                .where(
                        reservation.date.eq(date)
                                .and(reservation.sender.eq(user).or(reservation.receiver.eq(user)))
                                .and(reservation.reservationStatus.in(ReservationStatus.COMPLETED))
                )
                .fetch();
    }

}
