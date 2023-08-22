package com.shallwe.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shallwe.domain.user.domain.User;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
public class UpdateReservationReq {

    private Long id;
    private Long persons;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime date;
    private User sender;
    private User receiver;
    private String phone_number;
    private String invitation_img;
    private String invitation_comment;

}
