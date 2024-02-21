package com.shallwe.domain.reservation.domain.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.shallwe.domain.experiencegift.domain.QExperienceGift.*;
import static com.shallwe.domain.experiencegift.domain.QSubtitle.*;
import static com.shallwe.domain.memoryphoto.domain.QMemoryPhoto.memoryPhoto;
import static com.shallwe.domain.reservation.domain.QReservation.*;
import static com.shallwe.domain.user.domain.QUser.*;

@RequiredArgsConstructor
@Repository
public class ReservationQuerydslRepositoryImpl implements ReservationQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<ReceiveGiftDetailRes> findReservationsByPhoneNumberAndReservationStatusIn(String phoneNumber) {
        return queryFactory
                .select(new QReceiveGiftDetailRes(
                        reservation.id,
                        reservation.reservationStatus,
                        experienceGift.id,
                        experienceGift.title,
                        experienceGift.subtitle.title,
                        reservation.date,
                        reservation.time,
                        new QUserDetailRes(
                                user.id,
                                user.name,
                                user.birthDay,
                                user.age,
                                user.phoneNumber,
                                user.email,
                                user.profileImgUrl,
                                user.gender,
                                user.status
                                ),
                        reservation.invitationImg,
                        reservation.invitationComment
                ))
                .from(reservation)
                .leftJoin(experienceGift).on(reservation.experienceGift.eq(experienceGift))
                .leftJoin(experienceGift.subtitle).on(experienceGift.subtitle.eq(subtitle))
                .leftJoin(user).on(reservation.sender.eq(user))
                .where(
                        reservation.phoneNumber.eq(phoneNumber)
                                .and(reservation.reservationStatus.in(ReservationStatus.BOOKED, ReservationStatus.COMPLETED))
                )
                .orderBy(reservation.reservationStatus.asc()).orderBy(reservation.date.desc())
                .fetch();
    }

    @Override
    public List<SendGiftDetailRes> findReservationsBySenderAndReservationStatusIn(User sender) {
        return queryFactory
                .select(new QSendGiftDetailRes(
                        reservation.id,
                        reservation.reservationStatus,
                        experienceGift.id,
                        experienceGift.title,
                        experienceGift.subtitle.title,
                        reservation.date,
                        reservation.time,
                        new QUserDetailRes(
                                user.id,
                                user.name,
                                user.birthDay,
                                user.age,
                                user.phoneNumber,
                                user.email,
                                user.profileImgUrl,
                                user.gender,
                                user.status
                        ),
                        reservation.invitationImg,
                        reservation.invitationComment
                ))
                .from(reservation)
                .leftJoin(experienceGift).on(reservation.experienceGift.eq(experienceGift))
                .leftJoin(experienceGift.subtitle).on(experienceGift.subtitle.eq(subtitle))
                .leftJoin(user).on(reservation.sender.eq(user))
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
            .leftJoin(reservation.experienceGift, experienceGift)
            .leftJoin(reservation.experienceGift.subtitle, subtitle)
            .leftJoin(reservation.memoryPhotos, memoryPhoto)
            .leftJoin(memoryPhoto.uploader)
            .leftJoin(reservation.sender)
            .leftJoin(reservation.receiver)
            .where(
                reservation.date.eq(date)
                    .and(reservation.sender.eq(user).or(reservation.receiver.eq(user)))
                    .and(reservation.reservationStatus.in(ReservationStatus.COMPLETED, ReservationStatus.USING))
            )
            .fetch();
    }

}
