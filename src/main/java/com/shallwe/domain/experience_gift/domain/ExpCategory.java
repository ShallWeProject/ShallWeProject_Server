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
public class ExpCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expCategoryId;

    private String expCategory;

    @Builder
    public ExpCategory(Long ExpCategoryId, String expCategory){
        this.expCategoryId =ExpCategoryId;
        this.expCategory=expCategory;
    }

}
