package com.shallwe.domain.experienceGift.application;

import com.shallwe.domain.experienceGift.dto.response.*;
import com.shallwe.domain.experienceGift.dto.request.ExperienceReq;
import com.shallwe.domain.experienceGift.dto.response.ExperienceDetailRes;
import com.shallwe.domain.experienceGift.dto.response.ExperienceExpCategoryRes;
import com.shallwe.domain.experienceGift.dto.response.ExperienceRes;
import com.shallwe.domain.experienceGift.dto.response.ExperienceSttCategoryRes;
import com.shallwe.global.config.security.token.UserPrincipal;

import java.util.List;

public interface ExperienceGiftService {

    ExperienceDetailRes createExperience(final UserPrincipal userPrincipal,final ExperienceReq experienceReq);

    List<ExperienceRes> searchExperience(UserPrincipal userPrincipal,String title);

    ExperienceDetailRes getExperienceDetails(final UserPrincipal userPrincipal,Long ExperienceGiftId);

    List<ExperienceSttCategoryRes> highSttCategoryPricedGift(UserPrincipal userPrincipal,Long SttCategoryId);

    List<ExperienceSttCategoryRes> lowSttCategoryPricedGift(UserPrincipal userPrincipal, Long sttCategoryId);

    List<ExperienceExpCategoryRes> highExpCategoryPricedGift(UserPrincipal userPrincipal, Long expCategoryId);

    List<ExperienceExpCategoryRes> lowExpCategoryPricedGift(UserPrincipal userPrincipal, Long expCategoryId);

    List<ExperienceSttCategoryRes> getPopularSttGift(UserPrincipal userPrincipal, Long sttCategoryId);

    List<ExperienceExpCategoryRes> getPopulaExpGift(UserPrincipal userPrincipal, Long expCategoryId);

    ExperienceMainRes mainPage(UserPrincipal userPrincipal);

    List<ExperienceRes> getAllPopularGift(UserPrincipal userPrincipal);
}
