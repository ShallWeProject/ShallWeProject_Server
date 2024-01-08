package com.shallwe.domain.reservation.domain;


import com.shallwe.domain.common.BaseEntity;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.memoryphoto.domain.MemoryPhoto;
import com.shallwe.domain.reservation.dto.request.UserReservationCreate;
import com.shallwe.domain.reservation.dto.request.UpdateReservationReq;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Table(name = "Reservation")
@Where(clause = "status = 'ACTIVE'")
public class Reservation extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "experience_gift_id", nullable = false)
  @Schema(description = "선물 ID")
  private ExperienceGift experienceGift;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false)
  @Schema(description = "사장ID")
  private ShopOwner owner;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_id")
  @Schema(description = "보내는이 ID")
  private User sender;

  @Column(name = "person")
  private Long persons;

  @Schema(description = "예약 날짜, YYYY-DD-MM ")
  @Column(name = "date")
  private LocalDate date;

  @Schema(description = "예약 시간, HH:MM")
  @Column(name = "time")
  private LocalTime time;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "receiver_id")
  private User receiver;

  @Column(name = "phone_umber")
  private String phoneNumber;

  @Column(name = "invitation_img")
  private String invitationImg;

  @Column(name = "invitation_comment")
  private String invitationComment;

  @Schema(description = "예약 상태", allowableValues = {"CANCELED", "WAITING", "BOOKED", "CONFIRMED",
      "COMPLETED", "USING"})
  @Column(name = "reservation_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private ReservationStatus reservationStatus;

  @OneToMany(mappedBy = "reservation",fetch = FetchType.LAZY)
  private List<MemoryPhoto> memoryPhotos = new ArrayList<>();

  @Builder
  public Reservation(ExperienceGift experienceGift, ShopOwner owner, LocalDate date,
      LocalTime time,
      ReservationStatus reservationStatus) {
    this.experienceGift = experienceGift;
    this.owner = owner;
    this.date = date;
    this.time = time;
    this.reservationStatus = reservationStatus;
  }

  public void updateReservation(UpdateReservationReq updateReq) {
    this.date = Optional.ofNullable(updateReq.getDate()).orElse(this.date);
    this.time = Optional.ofNullable(updateReq.getTime()).orElse(this.time);
  }

  public void updateUserReservationRequest(UserReservationCreate reservationRequest, User sender,
      User receiver) {
    this.sender = sender;
    this.receiver = receiver;
    this.phoneNumber = reservationRequest.getPhoneNumber();
    this.invitationComment = reservationRequest.getInvitationComment();
    this.persons = reservationRequest.getPersons();
    this.invitationImg = reservationRequest.getImageURL();
  }

  public void updateStatus(ReservationStatus status) {
    this.reservationStatus = status;
  }
}
