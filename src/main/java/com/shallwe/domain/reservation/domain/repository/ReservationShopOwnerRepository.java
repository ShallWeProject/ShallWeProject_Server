package com.shallwe.domain.reservation.domain.repository;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationShopOwnerRepository extends JpaRepository<Reservation,Long> {

  Long countByExperienceGift_ShopOwnerAndReservationStatus(ShopOwner shopOwner, ReservationStatus status);
}
