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

    private String identification;

    private String businessRegistration;

    private String bankbook;

    @OneToMany
    private List<Reservation> reservationList;

    public void changePassword(String password) {
        this.password = password;
    }

    @Builder
    public ShopOwner(String name, String phoneNumber, String password, Boolean marketingConsent, String identification, String businessRegistration, String bankbook, List<Reservation> reservationList) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.marketingConsent = marketingConsent;
        this.identification = identification;
        this.businessRegistration = businessRegistration;
        this.bankbook = bankbook;
        this.reservationList = reservationList;
    }

    public void updateIdentification(String identification) {
        this.identification = identification;
    }

    public void updateBusinessRegistration(String businessRegistration) {
        this.businessRegistration = businessRegistration;
    }

    public void updateBankbook(String bankbook) {
        this.bankbook = bankbook;
    }

}
