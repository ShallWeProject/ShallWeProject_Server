package com.shallwe.domain.experiencegift.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class ExperienceCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expCategory;

    private String imageKey;

    @Builder
    public ExperienceCategory(String expCategory, String imageKey){
        this.expCategory=expCategory;
        this.imageKey=imageKey;
    }

}
