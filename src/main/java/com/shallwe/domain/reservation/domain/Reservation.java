package com.shallwe.domain.reservation.domain;


import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import com.shallwe.domain.reservation.dto.UpdateReservationReq;
import com.shallwe.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_gift_id")
    private ExperienceGift experienceGift;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long persons;

    private LocalDateTime date;

    private String receiver;

    private String phone_number;

    private String invitationImg;

    private String invitationComment;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Builder
    public Reservation(Long id, ExperienceGift experienceGift, User user, Long persons, LocalDateTime date, String receiver, String phone_number, String invitationImg, String invitationComment, ReservationStatus reservationStatus) {
        this.id = id;
        this.experienceGift = experienceGift;
        this.user = user;
        this.persons = persons;
        this.date = date;
        this.receiver = receiver;
        this.phone_number = phone_number;
        this.invitationImg = invitationImg;
        this.invitationComment = invitationComment;
        this.reservationStatus = reservationStatus;
    }

    public void updateReservation(UpdateReservationReq updateReq) {
        this.persons = Optional.ofNullable(updateReq.getPersons()).orElse(this.persons);
        this.date = Optional.ofNullable(updateReq.getDate()).orElse(this.date);
        this.receiver = Optional.ofNullable(updateReq.getReceiver()).orElse(this.receiver);
        this.phone_number = Optional.ofNullable(updateReq.getPhone_number()).orElse(this.phone_number);
        this.invitationImg = Optional.ofNullable(updateReq.getInvitation_img()).orElse(this.invitationImg);
        this.invitationComment = Optional.ofNullable(updateReq.getInvitation_comment()).orElse(this.invitationComment);
    }

}
