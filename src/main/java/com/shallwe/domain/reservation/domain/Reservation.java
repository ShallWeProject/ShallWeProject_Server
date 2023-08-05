package com.shallwe.domain.reservation.domain;


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
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experienceGiftId")

    private ExperienceGift experienceGift;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long persons;

    private LocalDateTime date;

    private String receiver;

    private String phone_number;

    private String sender;

    private String invitation_img;

    private String invitation_comment;

    @Enumerated(EnumType.STRING)
    private Reservation_status reservation_status;


    @Builder
    public Reservation(Long id, Long gift_id, User user, Long persons, LocalDateTime date, String sender, String receiver, String phone_number, String invitation_img, String invitation_comment, Reservation_status reservation_status) {
        this.id = id;
        this.user = user;
        this.persons = persons;
        this.date = date;
        this.receiver = receiver;
        this.phone_number = phone_number;
        this.invitation_img = invitation_img;
        this.invitation_comment = invitation_comment;
        this.reservation_status = reservation_status;
    }

    public void updateReservation(UpdateReservationReq updateReq) {
        this.persons = Optional.ofNullable(updateReq.getPersons()).orElse(this.persons);
        this.date = Optional.ofNullable(updateReq.getDate()).orElse(this.date);
        this.receiver = Optional.ofNullable(updateReq.getReceiver()).orElse(this.receiver);
        this.phone_number = Optional.ofNullable(updateReq.getPhone_number()).orElse(this.phone_number);
        this.invitation_img = Optional.ofNullable(updateReq.getInvitation_img()).orElse(this.invitation_img);
        this.invitation_comment = Optional.ofNullable(updateReq.getInvitation_comment()).orElse(this.invitation_comment);
    }

}
