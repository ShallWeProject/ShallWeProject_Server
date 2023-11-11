package com.shallwe.domain.reservation.domain;


import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.memoryphoto.domain.MemoryPhoto;
import com.shallwe.domain.reservation.dto.UpdateReservationReq;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.user.domain.User;
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
    private ExperienceGift experienceGift;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private ShopOwner owner;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    private Long persons;

    private LocalDate date;

    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private String phoneNumber;

    private String invitationImg;

    private String invitationComment;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @OneToMany(mappedBy = "reservation")
    List<MemoryPhoto> memoryPhotos = new ArrayList<>();

    public void updateReservation(UpdateReservationReq updateReq) {
        this.persons = Optional.ofNullable(updateReq.getPersons()).orElse(this.persons);
        this.date = Optional.ofNullable(updateReq.getDate()).orElse(this.date);
        this.time = Optional.ofNullable(LocalTime.of(updateReq.getTime(),0)).orElse(this.time);
        this.receiver = Optional.ofNullable(updateReq.getReceiver()).orElse(this.getReceiver());
        this.phoneNumber = Optional.ofNullable(updateReq.getPhone_number()).orElse(this.phoneNumber);
        this.invitationImg = Optional.ofNullable(updateReq.getInvitation_img()).orElse(this.invitationImg);
        this.invitationComment = Optional.ofNullable(updateReq.getInvitation_comment()).orElse(this.invitationComment);
    }

    public int getHour(){
        return time.getHour();
    }
}
