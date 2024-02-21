package com.shallwe.domain.experiencegift.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "experience_gift_experience_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExperienceGiftExperienceCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "experience_gift_id")
    private ExperienceGift experienceGift;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "experience_category_id")
    private ExperienceCategory experienceCategory;

    @Builder
    public ExperienceGiftExperienceCategory(ExperienceGift experienceGift, ExperienceCategory experienceCategory) {
        this.experienceGift = experienceGift;
        this.experienceCategory = experienceCategory;
    }

}
