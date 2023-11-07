package com.shallwe.domain.experiencegift.domain;

import com.shallwe.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class SttCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sttCategoryId;

    private String sttCategory;
    private String imageKey;

    @Builder
    public SttCategory(String sttCategory,String imageKey){
        this.sttCategory=sttCategory;
        this.imageKey=imageKey;
    }
}
