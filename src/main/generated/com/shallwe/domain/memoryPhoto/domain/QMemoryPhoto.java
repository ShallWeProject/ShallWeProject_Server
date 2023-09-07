package com.shallwe.domain.memoryPhoto.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemoryPhoto is a Querydsl query type for MemoryPhoto
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemoryPhoto extends EntityPathBase<MemoryPhoto> {

    private static final long serialVersionUID = 286636941L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemoryPhoto memoryPhoto = new QMemoryPhoto("memoryPhoto");

    public final com.shallwe.domain.common.QBaseEntity _super = new com.shallwe.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memoryImgUrl = createString("memoryImgUrl");

    public final com.shallwe.domain.reservation.domain.QReservation reservation;

    //inherited
    public final EnumPath<com.shallwe.domain.common.Status> status = _super.status;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemoryPhoto(String variable) {
        this(MemoryPhoto.class, forVariable(variable), INITS);
    }

    public QMemoryPhoto(Path<? extends MemoryPhoto> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemoryPhoto(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemoryPhoto(PathMetadata metadata, PathInits inits) {
        this(MemoryPhoto.class, metadata, inits);
    }

    public QMemoryPhoto(Class<? extends MemoryPhoto> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reservation = inits.isInitialized("reservation") ? new com.shallwe.domain.reservation.domain.QReservation(forProperty("reservation"), inits.get("reservation")) : null;
    }

}

