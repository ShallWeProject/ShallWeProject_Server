package com.shallwe.domain.experience_gift.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
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

    @Builder
    public Explanation(String explanationKey, String description){
        this.explanationKey=explanationKey;
        this.description=description;
    }



}
