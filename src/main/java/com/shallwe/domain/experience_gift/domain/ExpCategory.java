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
    private Long ExpCategoryId;

    private String expCategory;

    @Builder
    public ExpCategory(Long ExpCategoryId, String expCategory){
        this.ExpCategoryId=ExpCategoryId;
        this.expCategory=expCategory;
    }
}
