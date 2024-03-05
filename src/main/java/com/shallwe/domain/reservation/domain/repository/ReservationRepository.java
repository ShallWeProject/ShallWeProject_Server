package com.shallwe.domain.reservation.domain.repository;


import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.reservation.domain.Reservation;

import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.user.domain.User;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationQuerydslRepository {

    Optional<List<Reservation>> findAllBySenderId(Long userId);

    Optional<List<Reservation>> findAllByExperienceGift(ExperienceGift experienceGift);

    Optional<List<Reservation>> findAllByExperienceGiftAndReservationStatus(ExperienceGift experienceGift, ReservationStatus reservationStatus);

    @EntityGraph(attributePaths = "experienceGift")
    Optional<Reservation> findByDateAndTimeAndExperienceGift(LocalDate date, LocalTime time, ExperienceGift experienceGift);

    Optional<List<Reservation>> findAllByExperienceGiftAndDate(ExperienceGift experienceGift, LocalDate date);

    @EntityGraph(attributePaths = {"experienceGift", "experienceGift.shopOwner", "sender", "receiver"})
    Optional<Reservation> findReservationById(Long reservationId);

}
