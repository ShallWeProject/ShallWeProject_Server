package com.shallwe.domain.memoryphoto.domain;

import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
@Getter
@Where(clause = "status = 'ACTIVE'")
public class MemoryPhoto extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memoryImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id")
    private User uploader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Builder
    public MemoryPhoto(String memoryImgUrl, User uploader, Reservation reservation) {
        this.memoryImgUrl = memoryImgUrl;
        this.uploader = uploader;
        this.reservation = reservation;
    }

}
