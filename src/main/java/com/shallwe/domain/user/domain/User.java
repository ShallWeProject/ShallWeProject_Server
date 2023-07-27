package com.shallwe.domain.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;

import com.shallwe.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Where(clause = "status = 'ACTIVE'")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String birthDay;

    private Integer age;

    @Email
    private String email;

    private String password;

    private String profileImgUrl;

    private Gender gender;

    private Boolean marketingConsent;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(Long id, String name, String birthDay, Integer age, String email, String password, String profileImgUrl, Gender gender, Boolean marketingConsent, Provider provider, String providerId, Role role) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.age = age;
        this.email = email;
        this.password = password;
        this.profileImgUrl = profileImgUrl;
        this.gender = gender;
        this.marketingConsent = marketingConsent;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

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

}
