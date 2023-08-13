package com.shallwe.domain.auth.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class VerificationCode extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private String code;

    private LocalDateTime expiryDate;

    @Builder
    public VerificationCode(Long id, String phoneNumber, String code, LocalDateTime expiryDate) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.expiryDate = expiryDate;
    }

}
