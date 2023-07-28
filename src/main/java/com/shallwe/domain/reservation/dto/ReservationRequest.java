package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.Reservation_status;
import com.shallwe.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter

public class ReservationRequest {
    private Long id;
    //private Long gift_id;
    private Long persons;
    private LocalDate date;
    private String sender;
    private String receiver;
    private String phone_number;
    private String invitation_img;
    private String invitation_comment;
    private Reservation_status reservation_status;

    @Builder
    public ReservationRequest(Long id, /*Long gift_id*/ Long persons, LocalDate date, String sender, String receiver, String phone_number, String invitation_img, String invitation_comment, Reservation_status reservation_status){
        this.id = id;
//        this.gift_id = gift_id;
        this.persons=persons;
        this.date  = date;
        this.sender = sender;
        this.receiver =receiver;
        this.phone_number = phone_number;
        this.invitation_img= invitation_img;
        this.invitation_comment=invitation_comment;
        this.reservation_status=reservation_status;
    }

    public static Reservation toEntity(ReservationRequest reservationRequest, User user){
        return Reservation.builder()
                .id(reservationRequest.getId())
                .user(user)
                .persons(reservationRequest.getPersons())
                .date(reservationRequest.getDate())
                .receiver(reservationRequest.getReceiver())
                .phone_number(reservationRequest.getPhone_number())
                .invitation_img(reservationRequest.getInvitation_img())
                .invitation_comment(reservationRequest.getInvitation_comment())
                .reservation_status(reservationRequest.getReservation_status())
                .build();
    }
}
