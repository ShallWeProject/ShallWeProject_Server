package com.shallwe.domain.experience_gift.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubtitle is a Querydsl query type for Subtitle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubtitle extends EntityPathBase<Subtitle> {

    private static final long serialVersionUID = -1054495376L;

    public static final QSubtitle subtitle = new QSubtitle("subtitle");

    public final com.shallwe.domain.common.QBaseEntity _super = new com.shallwe.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final EnumPath<com.shallwe.domain.common.Status> status = _super.status;

    public final NumberPath<Long> SubtitleId = createNumber("SubtitleId", Long.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSubtitle(String variable) {
        super(Subtitle.class, forVariable(variable));
    }

    public QSubtitle(Path<? extends Subtitle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubtitle(PathMetadata metadata) {
        super(Subtitle.class, metadata);
    }

}

