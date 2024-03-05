package com.shallwe.domain.shopowner.domain;

import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.reservation.domain.Reservation;
import jakarta.persistence.*;

import java.util.List;

import lombok.*;
import org.hibernate.annotations.Where;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "Shop_Owner")
public class ShopOwner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "marketing_consent")
    private Boolean marketingConsent;

    @Column(name = "identification")
    private String identification;

    @Column(name = "business_registration")
    private String businessRegistration;

    @Column(name = "bankbook")
    private String bankbook;

    public void changePassword(String password) {
        this.password = password;
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

    @Builder
    public ShopOwner(String name, String phoneNumber, String password, Boolean marketingConsent, String identification, String businessRegistration, String bankbook) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.marketingConsent = marketingConsent;
        this.identification = identification;
        this.businessRegistration = businessRegistration;
        this.bankbook = bankbook;
    }

}