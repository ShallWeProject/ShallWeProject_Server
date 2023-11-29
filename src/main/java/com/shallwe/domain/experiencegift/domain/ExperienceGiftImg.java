package com.shallwe.domain.experiencegift.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class ExperienceGiftImg extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long experienceGiftImgId;

    @ManyToOne
    @JoinColumn(nullable = false,name = "experienceGift_id")
    private ExperienceGift experienceGift;

    private String imgKey;

}
