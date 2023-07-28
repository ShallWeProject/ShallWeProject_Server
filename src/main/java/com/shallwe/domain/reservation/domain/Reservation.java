package com.shallwe.domain.reservation.domain;

import com.shallwe.domain.reservation.dto.ReservationRequest;
import com.shallwe.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

//    @OneToOne
//    @JoinColumn(name = "gift_id")
//    private Long gift_id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long persons;

    private LocalDate date;

    private String sender;

    private String receiver;

    private String phone_number;

    private String invitation_img;

    private String invitation_comment;

    @Enumerated(EnumType.STRING)
    private Reservation_status reservation_status;



    @Builder
    public Reservation(Long id ,User user, Long gift_id, Long persons, LocalDate date, String receiver, String phone_number, String invitation_img, String invitation_comment, Reservation_status reservation_status){
        this.id = id;
        this.user= user;
        this.persons = persons;
        this.date = date;
        this.sender = user.getName();
        this.receiver = receiver;
        this.phone_number = phone_number;
        this.invitation_img = invitation_img;
        this.invitation_comment = invitation_comment;
        this.reservation_status = reservation_status;
    }
public void cancelReservation(){
        this.reservation_status = Reservation_status.CANCELLED;
}

/*
    public void updateInvitation_img(String invitation_comment){this.invitation_comment = invitation_comment;}

    public void updateInvitation_comment(String invitation_comment){this.invitation_comment =invitation_comment ;}

    public void updateReservation_status(Reservation_status reservation_status){this.reservation_status = reservation_status;}
*/

    public void updateReservation(ReservationRequest updateRequest) {
        if (updateRequest.getPersons() != null) {
            this.persons = updateRequest.getPersons();
        }
        if (updateRequest.getDate() != null) {
            this.date = updateRequest.getDate();
        }
        if (updateRequest.getSender() != null) {
            this.sender = updateRequest.getSender();
        }
        if (updateRequest.getReceiver() != null) {
            this.receiver = updateRequest.getReceiver();
        }
        if (updateRequest.getPhone_number() != null) {
            this.phone_number = updateRequest.getPhone_number();
        }
        if (updateRequest.getInvitation_img() != null) {
            this.invitation_img = updateRequest.getInvitation_img();
        }
        if (updateRequest.getInvitation_comment() != null) {
            this.invitation_comment = updateRequest.getInvitation_comment();
        }
        if (updateRequest.getReservation_status() != null) {
            this.reservation_status = updateRequest.getReservation_status();
        }
    }



}
