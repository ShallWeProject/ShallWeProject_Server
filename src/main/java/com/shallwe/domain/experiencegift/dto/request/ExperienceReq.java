package com.shallwe.domain.experiencegift.dto.request;

import com.shallwe.domain.experiencegift.domain.AvailableDate;
import com.shallwe.domain.experiencegift.domain.ExpCategory;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.SttCategory;
import com.shallwe.domain.experiencegift.domain.Subtitle;
import com.shallwe.domain.experiencegift.domain.TimeSlot;
import com.shallwe.domain.experiencegift.domain.TimeStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExperienceReq {

    private String title;
    private String thumbnail;
    private Long price;
    private Long expCategoryId;
    private Long sttCategoryId;
    private Long subtitleId;
    private String description;
    private List<AvailableDateReq> availableDates;

    @Builder
    public static ExperienceGift toEntity(ExperienceReq experienceReq, Subtitle subtitle, ExpCategory expCategory, SttCategory sttCategory) {

        ExperienceGift experienceGift = ExperienceGift.builder()
            .title(experienceReq.getTitle())
            .thumbnail(experienceReq.getThumbnail())
            .price(experienceReq.getPrice())
            .description(experienceReq.getDescription())
            .subtitle(subtitle)
            .expCategory(expCategory)
            .sttCategory(sttCategory)
            .availableDates(new ArrayList<>())
            .build();

        List<AvailableDate> availableDates = experienceReq.getAvailableDates().stream()
            .map(dateReq -> {
                List<TimeSlot> timeSlots = dateReq.getTimeSlots().stream()
                    .map(time -> TimeSlot.builder()
                        .time(time)
                        .status(TimeStatus.AVAILABLE)
                        .build())
                    .collect(Collectors.toList());

                AvailableDate availableDate = AvailableDate.builder()
                    .date(dateReq.getDate())
                    .availableTimeslot(timeSlots)
                    .experienceGift(experienceGift) // AvailableDate에 ExperienceGift 설정
                    .build();

                experienceGift.getAvailableDates().add(availableDate); // ExperienceGift에 AvailableDate 추가

                return availableDate;
            })
            .toList();

        return experienceGift;
    }
}
