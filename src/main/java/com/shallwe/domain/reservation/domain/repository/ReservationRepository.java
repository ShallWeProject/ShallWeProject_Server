package com.shallwe.domain.reservation.domain.repository;

import com.shallwe.domain.reservation.domain.Reservation;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
