package com.shallwe.domain.experiencegift.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class ExperienceGiftImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false,name = "experienceGift_id")
    private ExperienceGift experienceGift;

    private String imgKey;

    @Builder
    public ExperienceGiftImage(ExperienceGift experienceGift, String imgKey) {
        this.experienceGift = experienceGift;
        this.imgKey = imgKey;
    }

}
