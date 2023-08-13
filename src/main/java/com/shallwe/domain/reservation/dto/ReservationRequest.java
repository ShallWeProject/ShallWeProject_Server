package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.experience_gift.domain.ExpCategory;
import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

import static com.shallwe.domain.reservation.domain.ReservationStatus.BOOKED;



@Data
public class ReservationRequest {

    private Long experienceGiftId;
    private Long persons;
    private LocalDateTime date;
    private String receiverName;
    private String phoneNumber;
    private String invitationImg;
    private String invitationComment;
    private ReservationStatus reservationStatus;

    public static Reservation toEntity(final ReservationRequest reservationRequest,  User sender,  User receiver,ExperienceGift experienceGift) {
        System.out.println("receiver = " + receiver);
        try {
            Reservation toEntity = Reservation.builder()
                    .experienceGift(experienceGift)
                    .sender(sender)
                    .persons(reservationRequest.getPersons())
                    .date(reservationRequest.getDate())
                    .receiver(receiver)
                    .phoneNumber(reservationRequest.getPhoneNumber())
                    .invitationImg(reservationRequest.getInvitationImg())
                    .invitationComment(reservationRequest.getInvitationComment())
                    .reservationStatus(BOOKED)
                    .build();
            return toEntity;
        } catch (Exception e) {
            System.err.println("예외 발생: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
