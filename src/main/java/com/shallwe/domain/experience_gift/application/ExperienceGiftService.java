package com.shallwe.domain.experience_gift.application;

import com.shallwe.domain.experience_gift.dto.request.ExperienceReq;
import com.shallwe.domain.experience_gift.dto.response.ExperienceDetailRes;
import com.shallwe.domain.experience_gift.dto.response.ExperienceExpCategoryRes;
import com.shallwe.domain.experience_gift.dto.response.ExperienceRes;
import com.shallwe.domain.experience_gift.dto.response.ExperienceSttCategoryRes;
import com.shallwe.domain.reservation.dto.ReservationRequest;
import com.shallwe.domain.reservation.dto.ReservationResponse;
import com.shallwe.global.config.security.token.UserPrincipal;

import java.util.List;

public interface ExperienceGiftService {

    ExperienceDetailRes createExperience(final UserPrincipal userPrincipal,final ExperienceReq experienceReq);

    List<ExperienceRes> searchExperience(final UserPrincipal userPrincipal,String title);

    ExperienceDetailRes getExperienceDetails(final UserPrincipal userPrincipal,Long ExperienceGiftId);

    List<ExperienceSttCategoryRes> highSttCategoryPricedGift(UserPrincipal userPrincipal,Long SttCategoryId);

    List<ExperienceSttCategoryRes> lowSttCategoryPricedGift(UserPrincipal userPrincipal, Long sttCategoryId);

    List<ExperienceExpCategoryRes> highExpCategoryPricedGift(UserPrincipal userPrincipal, Long expCategoryId);

    List<ExperienceExpCategoryRes> lowExpCategoryPricedGift(UserPrincipal userPrincipal, Long expCategoryId);

}
