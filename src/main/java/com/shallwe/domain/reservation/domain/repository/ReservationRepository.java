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

    List<Reservation> findAllBySenderId(Long userId);
    Optional<Reservation> findBySenderIdAndId(Long userId, Long reservationId);

    Optional<List<Reservation>> findAllByExperienceGift(ExperienceGift experienceGift);

    Optional<Reservation> findByReceiverIdAndId(Long userId, Long reservationId);
    @Query("SELECT r FROM Reservation r WHERE r.experienceGift.experienceGiftId = :giftId")
    List<Reservation> findByExperienceGift_Id(@Param("giftId")Long giftId);

    Optional<List<Reservation>> findAllByExperienceGiftAndReservationStatus(ExperienceGift experienceGift, ReservationStatus reservationStatus);

    Optional<Reservation> findByDateAndTime(LocalDate date, LocalTime time);

    @EntityGraph(attributePaths = {"memoryPhotos"})
    List<Reservation> findAllByDateAndPhoneNumber(LocalDateTime date, String phoneNumber);

    @EntityGraph(attributePaths = {"experienceGift", "sender", "receiver", "experienceGift.subtitle",
    "experienceGift.expCategory", "experienceGift.sttCategory"})
    List<Reservation> findReservationBySenderAndReservationStatusIn(User user, List<ReservationStatus> reservationStatusList);

    @EntityGraph(attributePaths = {"experienceGift", "sender", "receiver", "experienceGift.subtitle",
            "experienceGift.expCategory", "experienceGift.sttCategory"})
    List<Reservation> findReservationByPhoneNumberAndReservationStatusIn(String phoneNUmber, List<ReservationStatus> reservationStatusList);

    @EntityGraph(attributePaths = {"memoryPhotos", "experienceGift", "experienceGift.subtitle"})
    List<Reservation> findAllByDateAndPhoneNumber(LocalDate date, String phoneNumber);

    Long countByExperienceGift_ShopOwnerAndReservationStatus(ShopOwner shopOwner, ReservationStatus status);
}
