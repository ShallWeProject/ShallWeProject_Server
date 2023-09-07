package com.shallwe.domain.experienceGift.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSttCategory is a Querydsl query type for SttCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSttCategory extends EntityPathBase<SttCategory> {

    private static final long serialVersionUID = 769451346L;

    public static final QSttCategory sttCategory1 = new QSttCategory("sttCategory1");

    public final com.shallwe.domain.common.QBaseEntity _super = new com.shallwe.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath imageKey = createString("imageKey");

    //inherited
    public final EnumPath<com.shallwe.domain.common.Status> status = _super.status;

    public final StringPath sttCategory = createString("sttCategory");

    public final NumberPath<Long> sttCategoryId = createNumber("sttCategoryId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSttCategory(String variable) {
        super(SttCategory.class, forVariable(variable));
    }

    public QSttCategory(Path<? extends SttCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSttCategory(PathMetadata metadata) {
        super(SttCategory.class, metadata);
    }

}

