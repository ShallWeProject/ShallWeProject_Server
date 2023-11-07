package com.shallwe.domain.experiencegift.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class ExpCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expCategoryId;

    private String expCategory;
    private String imageKey;

    @Builder
    public ExpCategory(String expCategory,String imageKey){
        this.expCategory=expCategory;
        this.imageKey=imageKey;
    }

}
