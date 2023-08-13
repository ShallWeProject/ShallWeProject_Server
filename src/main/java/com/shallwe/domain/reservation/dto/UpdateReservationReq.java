package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.user.domain.User;
import lombok.Data;
import java.time.LocalDateTime;


@Data
public class UpdateReservationReq {

    private Long id;
    private Long persons;
    private LocalDateTime date;
    private User sender;
    private User receiver;
    private String phone_number;
    private String invitation_img;
    private String invitation_comment;

}
