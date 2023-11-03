package com.shallwe.domain.shopowner.domain.application;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.shopowner.domain.dto.ScheduleManageList;
import com.shallwe.domain.shopowner.domain.dto.ScheduleManageList.Schedule;
import com.shallwe.domain.shopowner.domain.repository.ShopOwnerRepository;
import com.shallwe.global.config.security.token.UserPrincipal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopOwnerSericeImpl implements ShopOwnerService {

  private final ShopOwnerRepository shopOwnerRepository;

  @Override
  public ScheduleManageList getPresentScheduleList(UserPrincipal userPrincipal) {
    Long id = userPrincipal.getId();
    List<ExperienceGift> giftList = shopOwnerRepository.findAllById(id);
    List<Schedule> scheduleList1 = giftList.stream().map(experienceGift -> Schedule.builder()
        .build()
        .fromExperienceGift(experienceGift)).toList();

    return ScheduleManageList.builder()
        .scheduleList(scheduleList1)
        .build();
  }
}
