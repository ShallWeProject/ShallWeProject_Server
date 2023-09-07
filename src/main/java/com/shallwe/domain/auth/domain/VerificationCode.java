package com.shallwe.domain.auth.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class VerificationCode extends BaseEntity {

    @Id
    private String phoneNumber;

    private String code;

    private LocalDateTime expiryDate;

}
