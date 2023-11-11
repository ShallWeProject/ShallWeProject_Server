package com.shallwe.domain.reservation.domain;


import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.experiencegift.domain.AvailableDate;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.TimeSlot;
import com.shallwe.domain.memoryphoto.domain.MemoryPhoto;
import com.shallwe.domain.reservation.dto.UpdateReservationReq;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Where(clause = "status = 'ACTIVE'")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopOwner_id")
    private ShopOwner shopOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_gift_id")
    private ExperienceGift experienceGift;

    private Long persons;

    private String senderPhoneNumber;

    private String invitationImg;

    private String invitationComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "availableDate_id")
    private AvailableDate availableDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeslot_id")
    private TimeSlot timeSlot;

    @OneToMany(mappedBy = "reservation")
    List<MemoryPhoto> memoryPhotos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    public void updateReservation(UpdateReservationReq updateReq) {
        this.persons = Optional.ofNullable(updateReq.getPersons()).orElse(this.persons);
        this.availableDate = Optional.ofNullable(updateReq.getAvailableDate()).orElse(this.availableDate);
        this.timeSlot = Optional.ofNullable(updateReq.getTimeSlot()).orElse(this.timeSlot);
        this.receiver = Optional.ofNullable(updateReq.getReceiver()).orElse(this.getReceiver());
        this.senderPhoneNumber = Optional.ofNullable(updateReq.getPhone_number()).orElse(this.senderPhoneNumber);
        this.invitationImg = Optional.ofNullable(updateReq.getInvitation_img()).orElse(this.invitationImg);
        this.invitationComment = Optional.ofNullable(updateReq.getInvitation_comment()).orElse(this.invitationComment);
    }

}
