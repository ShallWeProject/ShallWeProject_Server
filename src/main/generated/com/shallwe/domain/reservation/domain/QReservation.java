package com.shallwe.domain.reservation.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = 661198765L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservation reservation = new QReservation("reservation");

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final com.shallwe.domain.experience_gift.domain.QExperienceGift experienceGift;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath invitation_comment = createString("invitation_comment");

    public final StringPath invitation_img = createString("invitation_img");

    public final NumberPath<Long> persons = createNumber("persons", Long.class);

    public final StringPath phone_number = createString("phone_number");

    public final StringPath receiver = createString("receiver");

    public final EnumPath<Reservation_status> reservation_status = createEnum("reservation_status", Reservation_status.class);

    public final StringPath sender = createString("sender");

    public final com.shallwe.domain.user.domain.QUser user;

    public QReservation(String variable) {
        this(Reservation.class, forVariable(variable), INITS);
    }

    public QReservation(Path<? extends Reservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservation(PathMetadata metadata, PathInits inits) {
        this(Reservation.class, metadata, inits);
    }

    public QReservation(Class<? extends Reservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.experienceGift = inits.isInitialized("experienceGift") ? new com.shallwe.domain.experience_gift.domain.QExperienceGift(forProperty("experienceGift"), inits.get("experienceGift")) : null;
        this.user = inits.isInitialized("user") ? new com.shallwe.domain.user.domain.QUser(forProperty("user")) : null;
    }

}

