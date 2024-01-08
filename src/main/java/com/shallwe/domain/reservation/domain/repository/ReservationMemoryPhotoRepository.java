package com.shallwe.domain.reservation.domain.repository;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.user.domain.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationMemoryPhotoRepository extends JpaRepository<Reservation, Long>, ReservationQuerydslRepository{

  @EntityGraph(attributePaths = {"memoryPhotos"})
  Optional<List<Reservation>> findAllByDateAndPhoneNumber(LocalDateTime date, String phoneNumber);

  @EntityGraph(attributePaths = {"experienceGift", "sender", "receiver", "experienceGift.subtitle",
      "experienceGift.expCategory", "experienceGift.sttCategory"})
  List<Reservation> findReservationBySenderAndReservationStatusIn(
      User user, List<ReservationStatus> reservationStatusList);

  @EntityGraph(attributePaths = {"experienceGift", "sender", "receiver", "experienceGift.subtitle",
      "experienceGift.expCategory", "experienceGift.sttCategory"})
  List<Reservation> findReservationByPhoneNumberAndReservationStatusIn(String phoneNUmber, List<ReservationStatus> reservationStatusList);

  @EntityGraph(attributePaths = {"memoryPhotos", "experienceGift", "experienceGift.subtitle"})
  List<Reservation> findAllByDateAndPhoneNumber(LocalDate date, String phoneNumber);

}
