package com.shallwe.domain.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import com.shallwe.domain.common.BaseEntity;
import lombok.*;

import org.hibernate.annotations.Where;


@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String birthDay;

    private Integer age;

    private String phoneNumber;

    @Email
    private String email;

    private String password;

    private String profileImgUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Boolean marketingConsent;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void updateName(String name){
        this.name = name;
    }

    public void updateProfileImage(String imageUrl){
        this.profileImgUrl = imageUrl;
    }

    public void updateMarketingConsent(boolean status) {
        this.marketingConsent = status;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

    public void updateAge(Integer age) {
        this.age = age;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Builder
    public User(String name, String birthDay, Integer age, String phoneNumber, String email, String password, String profileImgUrl, Gender gender, Boolean marketingConsent, Provider provider, String providerId, Role role) {
        this.name = name;
        this.birthDay = birthDay;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.profileImgUrl = profileImgUrl;
        this.gender = gender;
        this.marketingConsent = marketingConsent;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

}
