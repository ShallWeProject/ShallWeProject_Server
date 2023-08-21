package com.shallwe.domain.experience_gift.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExplanation is a Querydsl query type for Explanation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExplanation extends EntityPathBase<Explanation> {

    private static final long serialVersionUID = -1566585407L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExplanation explanation = new QExplanation("explanation");

    public final com.shallwe.domain.common.QBaseEntity _super = new com.shallwe.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final QExperienceGift experienceGift;

    public final NumberPath<Long> explanationId = createNumber("explanationId", Long.class);

    public final StringPath explanationKey = createString("explanationKey");

    //inherited
    public final EnumPath<com.shallwe.domain.common.Status> status = _super.status;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QExplanation(String variable) {
        this(Explanation.class, forVariable(variable), INITS);
    }

    public QExplanation(Path<? extends Explanation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExplanation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExplanation(PathMetadata metadata, PathInits inits) {
        this(Explanation.class, metadata, inits);
    }

    public QExplanation(Class<? extends Explanation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.experienceGift = inits.isInitialized("experienceGift") ? new QExperienceGift(forProperty("experienceGift"), inits.get("experienceGift")) : null;
    }

}

