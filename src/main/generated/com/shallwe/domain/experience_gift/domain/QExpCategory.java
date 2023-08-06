package com.shallwe.domain.experience_gift.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExpCategory is a Querydsl query type for ExpCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExpCategory extends EntityPathBase<ExpCategory> {

    private static final long serialVersionUID = 167722467L;

    public static final QExpCategory expCategory1 = new QExpCategory("expCategory1");

    public final com.shallwe.domain.common.QBaseEntity _super = new com.shallwe.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath expCategory = createString("expCategory");

    public final NumberPath<Long> expCategoryId = createNumber("expCategoryId", Long.class);

    //inherited
    public final EnumPath<com.shallwe.domain.common.Status> status = _super.status;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QExpCategory(String variable) {
        super(ExpCategory.class, forVariable(variable));
    }

    public QExpCategory(Path<? extends ExpCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExpCategory(PathMetadata metadata) {
        super(ExpCategory.class, metadata);
    }

}

