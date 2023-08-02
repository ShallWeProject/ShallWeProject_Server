package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.Reservation_status;
import com.shallwe.domain.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

import static com.shallwe.domain.reservation.domain.Reservation_status.BOOKED;



@Data
public class ReservationRequest {
    private Long experienceGiftId;
    private Long userid;
    private Long persons;
    private LocalDateTime date;
    private String sender;
    private String receiver;
    private String phone_number;
    private String invitation_img;
    private String invitation_comment;
    private Reservation_status reservation_status;

    public static Reservation toEntity(final ReservationRequest reservationRequest, final User user) {
        try {
            Reservation toEntity = Reservation.builder()
                    .user(user)
                    .persons(reservationRequest.getPersons())
                    .date(reservationRequest.getDate())
                    .sender(user.getName())
                    .receiver(reservationRequest.getReceiver())
                    .phone_number(reservationRequest.getPhone_number())
                    .invitation_img(reservationRequest.getInvitation_img())
                    .invitation_comment(reservationRequest.getInvitation_comment())
                    .reservation_status(BOOKED)
                    .build();
            return toEntity;
        } catch (Exception e) {
            System.err.println("예외 발생: " + e.getMessage());
            e.printStackTrace();
            return null;
        }



    }
}
