package com.shallwe.domain.experienceGift.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Subtitle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subtitleId;

    private String title;

}
