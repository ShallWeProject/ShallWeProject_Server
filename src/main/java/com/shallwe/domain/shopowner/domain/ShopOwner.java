package com.shallwe.domain.shopowner.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public void changePassword(String password) {
        this.password = password;
    }

}
