package com.shallwe.domain.shopowner.domain.dto;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScheduleManageList {

  private final List<Schedule> scheduleList;

  @Builder
  public ScheduleManageList(List<Schedule> scheduleList) {
    this.scheduleList = scheduleList;
  }

  @Builder
  public static class Schedule {

    private Long id;
    private String title;
    private String subTitle;

    public Schedule(Long id, String title, String subTitle) {
      this.id = id;
      this.title = title;
      this.subTitle = subTitle;
    }

    public Schedule fromExperienceGift(ExperienceGift experienceGift){
      return Schedule.builder()
          .id(experienceGift.getExperienceGiftId())
          .title(experienceGift.getTitle())
          .subTitle(String.valueOf(experienceGift.getSubtitle()))
          .build();
    }
  }
}
