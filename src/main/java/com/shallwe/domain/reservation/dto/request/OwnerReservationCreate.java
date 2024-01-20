package com.shallwe.domain.reservation.dto.request;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.*;

import static com.shallwe.domain.reservation.domain.ReservationStatus.WAITING;

@Data
@NoArgsConstructor
public class OwnerReservationCreate {

  @Schema(description = "경험 선물 ID ", example = "1")
  private Long experienceGiftId;

  @Schema(description = "이용 가능 시간 map. \" 날짜1 \": \"시간1\", \"시간2\", \"시간3\" 형식",
      example = "\"dateTimeMap\": {\n"
          + "        \"2023-12-25\": [\"10:00\", \"13:00\"],\n"
          + "        \"2023-12-26\": [\"11:00\", \"14:00\"]\n"
          + "    }")
  private Map<LocalDate, List<LocalTime>> dateTimeMap;

  public static List<Reservation> toEntityForOwner(OwnerReservationCreate ownerReservationCreate,
      ExperienceGift experienceGift, ShopOwner owner) {
    List<Reservation> reservations = new ArrayList<>();

    for (Map.Entry<LocalDate, List<LocalTime>> entry : ownerReservationCreate.getDateTimeMap()
        .entrySet()) {
      LocalDate date = entry.getKey();
      List<LocalTime> times = entry.getValue();

            for (LocalTime time : times) {
                Reservation toEntity = Reservation.builder()
                        .experienceGift(experienceGift)
                        .date(date)
                        .time(time)
                        .owner(owner)
                        .reservationStatus(WAITING)
                        .build();
                reservations.add(toEntity);
            }
        }
        return reservations;
    }
}

