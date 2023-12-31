package com.shallwe.domain.auth.domain;

import com.shallwe.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Getter
public class Token extends BaseEntity {

    @Id
    private String userEmail;

//    @Column(columnDefinition = "test") // 컬럼 타입을 "test"로 변경
    private String refreshToken;

    public Token updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

}
