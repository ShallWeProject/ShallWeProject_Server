package com.shallwe.domain.experiencegift.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class SituationCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sttCategory;

    private String imageKey;

    @Builder
    public SituationCategory(String sttCategory, String imageKey){
        this.sttCategory=sttCategory;
        this.imageKey=imageKey;
    }

}
