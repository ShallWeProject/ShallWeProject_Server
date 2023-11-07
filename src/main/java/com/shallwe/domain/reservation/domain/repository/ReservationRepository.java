package com.shallwe.domain.reservation.domain.repository;


import com.shallwe.domain.reservation.domain.Reservation;

import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllBySenderId(Long userId);
    Optional<Reservation> findBySenderIdAndId(Long userId, Long reservationId);

    Optional<Reservation> findByReceiverIdAndId(Long userId, Long reservationId);
    @Query("SELECT r FROM Reservation r WHERE r.experienceGift.experienceGiftId = :giftId")
    List<Reservation> findByExperienceGift_Id(@Param("giftId")Long giftId);


    @EntityGraph(attributePaths = {"memoryPhotos"})
    List<Reservation> findAllByDateAndPhoneNumber(LocalDateTime date, String phoneNumber);

    @EntityGraph(attributePaths = {"experienceGift", "sender", "receiver", "experienceGift.subtitle",
    "experienceGift.expCategory", "experienceGift.sttCategory"})
    List<Reservation> findReservationBySenderAndReservationStatusIn(User user, List<ReservationStatus> reservationStatusList);

    @EntityGraph(attributePaths = {"experienceGift", "sender", "receiver", "experienceGift.subtitle",
            "experienceGift.expCategory", "experienceGift.sttCategory"})
    List<Reservation> findReservationByPhoneNumberAndReservationStatusIn(String phoneNUmber, List<ReservationStatus> reservationStatusList);

    @EntityGraph(attributePaths = {"memoryPhotos", "experienceGift", "experienceGift.subtitle"})
    List<Reservation> findAllByDateBetweenAndPhoneNumber(LocalDateTime startDateTime, LocalDateTime endDateTime, String phoneNumber);

    Long countByExperienceGift_ShopOwnerAndReservationStatus(ShopOwner shopOwner, ReservationStatus status);


}
