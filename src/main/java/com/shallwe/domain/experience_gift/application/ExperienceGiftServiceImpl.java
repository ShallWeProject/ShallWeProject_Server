package com.shallwe.domain.experience_gift.application;

import com.shallwe.domain.experience_gift.domain.ExperienceGift;
import com.shallwe.domain.experience_gift.dto.response.ExperienceDetailRes;
import com.shallwe.domain.experience_gift.dto.response.ExperienceExpCategoryRes;
import com.shallwe.domain.experience_gift.dto.response.ExperienceRes;
import com.shallwe.domain.experience_gift.dto.response.ExperienceSttCategoryRes;
import com.shallwe.domain.experience_gift.exception.ExperienceGiftNotFoundException;
import com.shallwe.domain.experience_gift.repository.ExperienceGiftRepository;
import com.shallwe.domain.user.domain.repository.UserRepository;
import com.shallwe.domain.user.exception.InvalidUserException;
import com.shallwe.global.config.security.token.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ExperienceGiftServiceImpl implements ExperienceGiftService{
    private final UserRepository userRepository;
    private final ExperienceGiftRepository experienceGiftRepository;

    @Override
    public List<ExperienceRes> searchExperience(final UserPrincipal userPrincipal, String title) {
        userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);
        return experienceGiftRepository.findByTitleContains(title)
                .stream().map(ExperienceRes::toDto).collect(Collectors.toList());
    }

    @Override
    public ExperienceDetailRes getExperienceDetails(final UserPrincipal userPrincipal,Long ExperienceGiftId) {
        userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);
        ExperienceGift experienceGift=experienceGiftRepository.findByExperienceGiftId(ExperienceGiftId).orElseThrow(ExperienceGiftNotFoundException::new);
        return ExperienceDetailRes.toDto(experienceGift);

    }

    @Override
    public List<ExperienceSttCategoryRes> highSttCategoryPricedGift(UserPrincipal userPrincipal,Long SttCategoryId) {
        userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);
        return experienceGiftRepository.findGiftsBySttCategoryIdOrderByPriceDesc(SttCategoryId)
                .stream().map(ExperienceSttCategoryRes::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ExperienceSttCategoryRes> lowSttCategoryPricedGift(UserPrincipal userPrincipal, Long SttCategoryId) {
        userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);
        return experienceGiftRepository.findGiftsBySttCategoryIdOrderByPriceAsc(SttCategoryId)
                .stream().map(ExperienceSttCategoryRes::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ExperienceExpCategoryRes> highExpCategoryPricedGift(UserPrincipal userPrincipal, Long ExpCategoryId) {
        userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);
        return experienceGiftRepository.findGiftsByExpCategoryIdOrderByPriceDesc(ExpCategoryId)
                .stream().map(ExperienceExpCategoryRes::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ExperienceExpCategoryRes> lowExpCategoryPricedGift(UserPrincipal userPrincipal, Long ExpCategoryId) {
        userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);
        return experienceGiftRepository.findGiftsByExpCategoryIdOrderByPriceAsc(ExpCategoryId)
                .stream().map(ExperienceExpCategoryRes::toDto).collect(Collectors.toList());
    }


}
