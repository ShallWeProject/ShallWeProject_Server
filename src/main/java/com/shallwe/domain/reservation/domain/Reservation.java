package com.shallwe.domain.reservation.domain;

import com.shallwe.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "gift_id")
//    private Long gift_id;

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
    public Reservation(Long id , Long gift_id,User user, Long persons, LocalDateTime date,String sender, String receiver, String phone_number, String invitation_img, String invitation_comment, Reservation_status reservation_status){
        this.id = id;
        this.user = user;
        this.persons = persons;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
        this.phone_number = phone_number;
        this.invitation_img = invitation_img;
        this.invitation_comment = invitation_comment;
        this.reservation_status = reservation_status;
    }
    public void updatePersons(Long persons){
        this.persons=persons;
    }
    public void updateDate(LocalDateTime date){
        this.date = date;
    }
    public void updateReceiver(String receiver){
        this.receiver=receiver;
    }
    public void updatePhoneNumber(String phone_number){
        this.phone_number=phone_number;
    }
    public void updateInvitation_img(String invitation_img){
        this.invitation_img=invitation_img;
    }
    public void updateInvitation_comment(String invitation_comment){
        this.invitation_comment=invitation_comment;
    }
    public void updateReservationStatus(Reservation_status reservationStatus){
        this.reservation_status = reservationStatus;
    }







}
