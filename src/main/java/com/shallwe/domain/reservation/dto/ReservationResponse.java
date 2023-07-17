package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.Reservation_status;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
@Data
@RequiredArgsConstructor
public class ReservationResponse {
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
    public ReservationResponse(Long id, /*Long gift_id*/ Long persons, LocalDate date, String sender, String receiver, String phone_number, String invitation_img, String invitation_comment, Reservation_status reservation_status){
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

    public static ReservationResponse toDto(Reservation reservation){
        ReservationResponseBuilder builder = ReservationResponse.builder()
                .id(reservation.getId())
//                .gift_id(reservation.getgift_id())
                .persons(reservation.getPersons())
                .date(reservation.getDate())
                .sender(reservation.getSender())
                .receiver(reservation.getReceiver())
                .invitation_img(reservation.getInvitation_img())
                .invitation_comment(reservation.getInvitation_comment())
                .reservation_status(reservation.getReservation_status());
                return builder.build();
    }

    public Long getId() {
        return id;
    }

    public Long getPersons() {
        return persons;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getInvitation_img() {
        return invitation_img;
    }

    public String getInvitation_comment() {
        return invitation_comment;
    }

    public Reservation_status getReservation_status() {
        return reservation_status;
    }
}
