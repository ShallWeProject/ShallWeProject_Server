package com.shallwe.domain.memory_photo.domain;

import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.reservation.domain.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Where(clause = "status = 'ACTIVE'")
public class MemoryPhoto extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memoryImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Builder
    public MemoryPhoto(Long id, String memoryImgUrl, Reservation reservation) {
        this.id = id;
        this.memoryImgUrl = memoryImgUrl;
        this.reservation = reservation;
    }

}
