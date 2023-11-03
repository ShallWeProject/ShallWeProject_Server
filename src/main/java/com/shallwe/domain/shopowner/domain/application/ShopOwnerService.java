package com.shallwe.domain.shopowner.domain.application;

import com.shallwe.domain.shopowner.domain.dto.ScheduleManageList;
import com.shallwe.global.config.security.token.UserPrincipal;

public interface ShopOwnerService {

  ScheduleManageList getPresentScheduleList(UserPrincipal userPrincipal);


}
