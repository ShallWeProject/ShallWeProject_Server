package com.shallwe.domain.experiencegift.domain;


import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.experiencegift.dto.request.AdminExperienceReq;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopOwner_id")
    private ShopOwner shopOwner;

    private String location;

    @OneToMany(mappedBy = "experienceGift")
    private List<ExperienceGiftImg> imgList=new ArrayList<>();

    public static ExperienceGift toDto(AdminExperienceReq req, Subtitle subtitle, ExpCategory expCategory, SttCategory sttCategory, ShopOwner shopOwner) {
        return ExperienceGift.builder()
                .title(req.getTitle())
                .subtitle(subtitle)
                .price(req.getPrice())
                .expCategory(expCategory)
                .sttCategory(sttCategory)
                .description(req.getDescription())
                .shopOwner(shopOwner)
                .location(req.getLocation())
                .build();
    }

    public void update(AdminExperienceReq adminExperienceReq, Subtitle subtitle, ExpCategory expCategory, SttCategory sttCategory, ShopOwner shopOwner) {
        this.title = adminExperienceReq.getTitle();
        this.description=adminExperienceReq.getDescription();
        this.location=adminExperienceReq.getLocation();
        this.price=adminExperienceReq.getPrice();
        this.subtitle = subtitle;
        this.expCategory = expCategory;
        this.sttCategory = sttCategory;
        this.shopOwner = shopOwner;
    }
}
