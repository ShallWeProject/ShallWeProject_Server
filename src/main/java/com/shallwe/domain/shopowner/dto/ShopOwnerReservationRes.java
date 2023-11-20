package com.shallwe.domain.shopowner.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;


@Data
public class ShopOwnerReservationRes {

  private String sender;
  private LocalDate date;
  private LocalTime time;
  private String phoneNum;
  private Long person;

  @Builder
  public ShopOwnerReservationRes(String sender, LocalDate date, LocalTime time, String phoneNum,
      Long person) {
    this.sender = sender;
    this.date = date;
    this.time = time;
    this.phoneNum = phoneNum;
    this.person = person;
  }

  public static List<ShopOwnerReservationRes> from(List<Reservation> reservationList) {
    return reservationList.stream()
        .map(reservation -> ShopOwnerReservationRes.builder()
            .date(reservation.getDate())
            .person(reservation.getPersons())
            .phoneNum(reservation.getPhoneNumber())
            .sender(reservation.getSender().getName())
            .time(reservation.getTime())
            .build()).toList();
  }
}
