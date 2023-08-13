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
public class SttCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sttCategoryId;

    private String sttCategory;

    @Builder
    public SttCategory(Long SttCategoryId, String sttCategory){
        this.sttCategoryId =SttCategoryId;
        this.sttCategory=sttCategory;
    }

}
