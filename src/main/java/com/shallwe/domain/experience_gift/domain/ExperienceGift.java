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
public class ExperienceGift extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long experienceGiftId;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubtitleId")
    private Subtitle subtitle; //fk

    private String thumbnail;
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExpCategoryId")
    private ExpCategory expCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SttCategoryId")
    private SttCategory sttCategory;

    private String description;

    @Builder
    public ExperienceGift(Long experienceGiftId, String title, String thumbnail, Long price, String description){
        this.experienceGiftId=experienceGiftId;
        this.title=title;
        this.thumbnail=thumbnail;
        this.price=price;
        this.description=description;
    }
}
