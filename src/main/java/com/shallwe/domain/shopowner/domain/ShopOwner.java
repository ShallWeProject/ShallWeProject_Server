package com.shallwe.domain.shopowner.domain;

import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.reservation.domain.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.Where;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Getter
@Where(clause = "status = 'ACTIVE'")
public class ShopOwner extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNumber;

    private String password;

    private Boolean marketingConsent;

    @OneToMany
    private List<Reservation> reservationList;

    public void changePassword(String password) {
        this.password = password;
    }

}
