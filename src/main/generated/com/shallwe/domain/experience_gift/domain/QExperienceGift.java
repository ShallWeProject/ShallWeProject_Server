package com.shallwe.domain.experience_gift.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExperienceGift is a Querydsl query type for ExperienceGift
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExperienceGift extends EntityPathBase<ExperienceGift> {

    private static final long serialVersionUID = 1528262930L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExperienceGift experienceGift = new QExperienceGift("experienceGift");

    public final com.shallwe.domain.common.QBaseEntity _super = new com.shallwe.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final QExpCategory expCategory;

    public final NumberPath<Long> experienceGiftId = createNumber("experienceGiftId", Long.class);

    public final StringPath giftImgKey = createString("giftImgKey");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    //inherited
    public final EnumPath<com.shallwe.domain.common.Status> status = _super.status;

    public final QSttCategory sttCategory;

    public final QSubtitle subtitle;

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QExperienceGift(String variable) {
        this(ExperienceGift.class, forVariable(variable), INITS);
    }

    public QExperienceGift(Path<? extends ExperienceGift> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExperienceGift(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExperienceGift(PathMetadata metadata, PathInits inits) {
        this(ExperienceGift.class, metadata, inits);
    }

    public QExperienceGift(Class<? extends ExperienceGift> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.expCategory = inits.isInitialized("expCategory") ? new QExpCategory(forProperty("expCategory")) : null;
        this.sttCategory = inits.isInitialized("sttCategory") ? new QSttCategory(forProperty("sttCategory")) : null;
        this.subtitle = inits.isInitialized("subtitle") ? new QSubtitle(forProperty("subtitle")) : null;
    }

}

