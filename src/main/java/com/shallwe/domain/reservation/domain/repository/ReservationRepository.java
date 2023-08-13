package com.shallwe.domain.reservation.domain.repository;

import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import com.shallwe.domain.reservation.domain.Reservation;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllBySenderId(Long userId);
    Optional<Reservation> findBySenderIdAndId(Long userId, Long reservationId);
    List<Reservation> findByExperienceGift(ExperienceGift experienceGift);

    @EntityGraph(attributePaths = {"memoryPhotos"})
    List<Reservation> findAllByDateAndPhoneNumber(LocalDateTime date, String phoneNumber);

}
