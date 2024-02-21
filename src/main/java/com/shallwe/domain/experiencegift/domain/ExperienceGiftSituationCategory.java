package com.shallwe.domain.experiencegift.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "experience_gift_situation_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExperienceGiftSituationCategory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "experience_gift_id")
    private ExperienceGift experienceGift;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "situation_category_id")
    private SituationCategory situationCategory;

    @Builder
    public ExperienceGiftSituationCategory(ExperienceGift experienceGift, SituationCategory situationCategory) {
        this.experienceGift = experienceGift;
        this.situationCategory = situationCategory;
    }

}
