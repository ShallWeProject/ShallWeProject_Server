package com.shallwe.domain.experiencegift.domain;

import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.experiencegift.dto.request.ExplanationReq;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class Explanation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long explanationId;

    private String stage;
    private String explanationKey;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="experienceGiftId")
    private ExperienceGift experienceGift;

    @Builder
    public Explanation(ExperienceGift experienceGift,String stage,String description, String explanationUrl){
        this.experienceGift=experienceGift;
        this.stage=stage;
        this.description=description;
        this.explanationKey=explanationUrl;
    }

    public static Explanation toDto(ExplanationReq explanationReq, ExperienceGift experienceGift) {
        return Explanation.builder()
                .experienceGift(experienceGift)
                .stage(explanationReq.getStage())
                .description(explanationReq.getDescription())
                .explanationUrl(AwsS3ImageUrlUtil.toUrl(explanationReq.getExplanationUrl()))
                .build();
    }

}
