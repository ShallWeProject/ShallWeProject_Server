package com.shallwe.domain.experiencegift.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AvailableDateReq {

  private LocalDate date;
  private List<String> timeSlots;

}
