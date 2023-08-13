package com.shallwe.domain.experience_gift.domain;


import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.reservation.domain.Reservation;
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
    @JoinColumn(name = "subtitle_id")
    private Subtitle subtitle; //fk

    private String thumbnail;
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exp_category_id")
    private ExpCategory expCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stt_category_id")
    private SttCategory sttCategory;

    private String description;
    private String giftImgKey;

    @Builder
    public ExperienceGift(Long experienceGiftId,String title, String thumbnail, Long price, String description,Subtitle subtitle,ExpCategory expCategory, SttCategory sttCategory, String giftImgKey){
        this.experienceGiftId=experienceGiftId;
        this.title=title;
        this.thumbnail=thumbnail;
        this.price=price;
        this.description=description;
        this.subtitle = subtitle;
        this.expCategory = expCategory;
        this.sttCategory = sttCategory;
        this.giftImgKey=giftImgKey;
    }

}
