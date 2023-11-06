package com.shallwe.domain.reservation.domain;


import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.memoryphoto.domain.MemoryPhoto;
import com.shallwe.domain.reservation.dto.UpdateReservationReq;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
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

    @OneToOne
    private AvailableTime dateTime; //한 예약엔 한 타임만 가능해야한다.

    private String senderPhoneNumber;

    private String invitationImg;

    private String invitationComment;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @OneToMany(mappedBy = "reservation")
    List<MemoryPhoto> memoryPhotos = new ArrayList<>();

    public void updateReservation(UpdateReservationReq updateReq) {
        this.persons = Optional.ofNullable(updateReq.getPersons()).orElse(this.persons);
        this.dateTime = Optional.ofNullable(updateReq.getDate()).orElse(this.dateTime);
        this.receiver = Optional.ofNullable(updateReq.getReceiver()).orElse(this.getReceiver());
        this.senderPhoneNumber = Optional.ofNullable(updateReq.getPhone_number()).orElse(this.senderPhoneNumber);
        this.invitationImg = Optional.ofNullable(updateReq.getInvitation_img()).orElse(this.invitationImg);
        this.invitationComment = Optional.ofNullable(updateReq.getInvitation_comment()).orElse(this.invitationComment);
    }

}
