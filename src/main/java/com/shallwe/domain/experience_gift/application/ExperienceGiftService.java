package com.shallwe.domain.experience_gift.application;

import com.shallwe.domain.experience_gift.dto.ExperienceDetailRes;
import com.shallwe.domain.experience_gift.dto.ExperienceRes;
import com.shallwe.global.config.security.token.UserPrincipal;

import java.util.List;

public interface ExperienceGiftService {
    List<ExperienceRes> searchExperience(final UserPrincipal userPrincipal,String title);

    ExperienceDetailRes getExperienceDetails(final UserPrincipal userPrincipal,Long ExperienceGiftId);
}
