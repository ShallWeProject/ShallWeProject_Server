package com.shallwe.domain.experiencegift.dto.response;

import com.shallwe.domain.experiencegift.domain.AvailableDate;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.TimeSlot;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AvailableDateRes {

  private LocalDate date;
  private List<String> timeSlots;

  @Builder
  public AvailableDateRes(LocalDate date, List<String> timeSlots) {
    this.date = date;
    this.timeSlots = timeSlots;
  }

  public static AvailableDateRes toDto(AvailableDate availableDate){
    return AvailableDateRes.builder()
        .date(availableDate.getDate())
        .timeSlots(availableDate.getAvailableTimeslot()
            .stream()
            .map(TimeSlot::getTime)
            .toList())
        .build();
  }
}
