package com.shallwe.domain.experiencegift.domain;


import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.experiencegift.dto.request.ShopOwnerExperienceReq;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "experience_gift")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExperienceGift extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Long price;

    @Column(name = "description")
    private String description;

    @Column(name = "gift_img_url")
    private String giftImgUrl;

    @Column(name = "location")
    private String location;

    @Column(name = "note")
    private String note;

    @Column(name = "reservation_count")
    private Long reservationCount; // 인기순 정렬을 위한 WAITING이 아닌 Reservation 개수 카운트

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtitle_id")
    private Subtitle subtitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_category_id")
    private ExperienceCategory experienceCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "situation_category_id")
    private SituationCategory situationCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopOwner_id")
    private ShopOwner shopOwner;

    @OneToMany(mappedBy = "experienceGift")
    private List<ExperienceGiftImage> imgList = new ArrayList<>();

    public static ExperienceGift toDto(ShopOwnerExperienceReq req, Subtitle subtitle, ExperienceCategory experienceCategory, ShopOwner shopOwner) {
        return ExperienceGift.builder()
                .title(req.getTitle())
                .subtitle(subtitle)
                .price(req.getPrice())
                .experienceCategory(experienceCategory)
                .description(req.getDescription())
                .shopOwner(shopOwner)
                .location(req.getLocation())
                .note(req.getNote())
                .build();
    }

    public void update(ShopOwnerExperienceReq shopOwnerExperienceReq, Subtitle subtitle, ExperienceCategory experienceCategory, ShopOwner shopOwner) {
        this.title = shopOwnerExperienceReq.getTitle();
        this.description = shopOwnerExperienceReq.getDescription();
        this.location = shopOwnerExperienceReq.getLocation();
        this.price = shopOwnerExperienceReq.getPrice();
        this.subtitle = subtitle;
        this.experienceCategory = experienceCategory;
        this.shopOwner = shopOwner;
        this.note=shopOwnerExperienceReq.getNote();
    }

    public void addReservationCount() {
        this.reservationCount++;
    }

    public void subtractReservationCount() {
        this.reservationCount--;
    }

    @Builder
    public ExperienceGift(String title, Subtitle subtitle, Long price, ExperienceCategory experienceCategory, SituationCategory situationCategory, String description, String giftImgUrl, ShopOwner shopOwner, String location, String note, List<ExperienceGiftImage> imgList) {
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.experienceCategory = experienceCategory;
        this.situationCategory = situationCategory;
        this.description = description;
        this.giftImgUrl = giftImgUrl;
        this.shopOwner = shopOwner;
        this.location = location;
        this.note = note;
        this.imgList = imgList;
    }

}
