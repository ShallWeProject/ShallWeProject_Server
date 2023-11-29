package com.shallwe.domain.reservation.domain;


import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.memoryphoto.domain.MemoryPhoto;
import com.shallwe.domain.reservation.dto.ReservationRequest;
import com.shallwe.domain.reservation.dto.ReservationUserReq;
import com.shallwe.domain.reservation.dto.UpdateReservationReq;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Getter
@Where(clause = "status = 'ACTIVE'")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_gift_id")
    @Schema(description = "선물 ID")
    private ExperienceGift experienceGift;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @Schema(description = "사장ID")
    private ShopOwner owner;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    @Schema(description = "보내는이 ID")
    private User sender;

    private Long persons;

    @Schema(description = "예약 날짜, YYYY-DD-MM ")
    private LocalDate date;

    @Schema(description = "예약 시간, HH:MM")
    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private String phoneNumber;

    private String invitationImg;

    private String invitationComment;

    @Enumerated(EnumType.STRING)
    @Schema(description = "예약 상태", allowableValues = {"CANCELED","WAITING","BOOKED","CONFIRMED","COMPLETED","USING"})
    private ReservationStatus reservationStatus;

    @OneToMany(mappedBy = "reservation")
    List<MemoryPhoto> memoryPhotos = new ArrayList<>();

    public void updateReservation(UpdateReservationReq updateReq) {
        this.persons = Optional.ofNullable(updateReq.getPersons()).orElse(this.persons);
        this.date = Optional.ofNullable(updateReq.getDate()).orElse(this.date);
        this.time = Optional.ofNullable(updateReq.getTime()).orElse(this.time);
        this.phoneNumber = Optional.ofNullable(updateReq.getPhone_number()).orElse(this.phoneNumber);
        this.invitationImg = Optional.ofNullable(updateReq.getInvitation_img()).orElse(this.invitationImg);
        this.invitationComment = Optional.ofNullable(updateReq.getInvitation_comment()).orElse(this.invitationComment);
    }

    public void updateUserReservationRequest(ReservationUserReq reservationRequest,User sender, User receiver){
        this.sender = sender;
        this.receiver = receiver;
        this.phoneNumber = reservationRequest.getPhoneNumber();
        this.invitationComment = reservationRequest.getInvitationComment();
        this.persons = reservationRequest.getPersons();
        this.invitationImg = reservationRequest.getImageKey();
    }

    public void updateStatus(ReservationStatus status){
        this.reservationStatus = status;
    }
}
