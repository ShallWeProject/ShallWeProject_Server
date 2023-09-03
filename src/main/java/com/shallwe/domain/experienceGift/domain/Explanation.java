package com.shallwe.domain.experienceGift.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Explanation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long explanationId;

    private String explanationKey;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="experienceGiftId")
    private ExperienceGift experienceGift;

}
