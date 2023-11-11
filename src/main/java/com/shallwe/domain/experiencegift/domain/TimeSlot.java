
package com.shallwe.domain.experiencegift.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String time;

    @Enumerated(EnumType.STRING)
    private TimeStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "availableDate_id")
    private AvailableDate availableDate;

    @Builder
    public TimeSlot(String time, TimeStatus status) {
        this.time = time;
        this.status = status;
    }
}
