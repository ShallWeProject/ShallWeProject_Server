package com.shallwe.domain.experienceGift.application;

import com.shallwe.domain.experienceGift.domain.ExpCategory;
import com.shallwe.domain.experienceGift.domain.ExperienceGift;
import com.shallwe.domain.experienceGift.domain.Explanation;
import com.shallwe.domain.experienceGift.domain.SttCategory;
import com.shallwe.domain.experienceGift.domain.repository.*;
import com.shallwe.domain.experienceGift.dto.response.*;
import com.shallwe.domain.experienceGift.exception.ExperienceGiftNotFoundException;
import com.shallwe.domain.experienceGift.dto.request.ExperienceReq;
import com.shallwe.domain.experienceGift.exception.*;
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
    private final ExpCategoryRepository expCategoryRepository;
    private final SttCategoryRepository sttCategoryRepository;
    private final SubtitleRepository subtitleRepository;
    private final ExplanationRepository explanationRepository;


    @Override
    public ExperienceMainRes mainPage(UserPrincipal userPrincipal) {
        userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);
        List<ExpCategory> expCategories = expCategoryRepository.findAll();
        List<SttCategory> sttCategories = sttCategoryRepository.findAll();
        return ExperienceMainRes.toDto(expCategories, sttCategories);
    }

    @Override
    public List<ExperienceRes> getAllPopularGift(UserPrincipal userPrincipal) {
        userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);

        List<ExperienceGift> popularGifts = experienceGiftRepository.findAllPopularGifts();
        return popularGifts.stream()
                .map(ExperienceRes::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ExperienceDetailRes createExperience(UserPrincipal userPrincipal,ExperienceReq experienceReq ) {
        userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);
        ExperienceGift experienceGift = ExperienceReq.toEntity(experienceReq,
                subtitleRepository.findBySubtitleId(experienceReq.getSubtitleId()).orElseThrow(SubtitleNotFoundException::new),
                expCategoryRepository.findByExpCategoryId(experienceReq.getExpCategoryId()).orElseThrow(ExpCategoryNotFoundException::new),
                sttCategoryRepository.findBySttCategoryId(experienceReq.getSttCategoryId()).orElseThrow(SttCategoryNotFoundException::new)
        );
        ExperienceGift savedGift = experienceGiftRepository.save(experienceGift);
        return ExperienceDetailRes.toDto(savedGift);
    }

    @Override
    public List<ExperienceRes> searchExperience(UserPrincipal userPrincipal, String title) {
        userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);
        return experienceGiftRepository.findByTitleContains(title)
                .stream().map(ExperienceRes::toDto).collect(Collectors.toList());
    }

    @Override
    public ExperienceDetailRes getExperienceDetails(final UserPrincipal userPrincipal,Long ExperienceGiftId) {
        userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);
        ExperienceGift experienceGift=experienceGiftRepository.findByExperienceGiftId(ExperienceGiftId).orElseThrow(ExperienceGiftNotFoundException::new);
        List<Explanation> explanations=explanationRepository.findByExperienceGift(experienceGift);
        return ExperienceDetailRes.toDetailDto(experienceGift,explanations);

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

    @Override
    public List<ExperienceSttCategoryRes> getPopularSttGift(UserPrincipal userPrincipal, Long sttCategoryId) {
        userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);

        List<ExperienceGift> popularGifts = experienceGiftRepository.findPopularGiftsBySttCategoryId(sttCategoryId);
        return popularGifts.stream()
                .map(ExperienceSttCategoryRes::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExperienceExpCategoryRes> getPopulaExpGift(UserPrincipal userPrincipal, Long expCategoryId) {
        userRepository.findById(userPrincipal.getId()).orElseThrow(InvalidUserException::new);

        List<ExperienceGift> popularGifts = experienceGiftRepository.findPopularGiftsByExpCategoryId(expCategoryId);
        return popularGifts.stream()
                .map(ExperienceExpCategoryRes::toDto)
                .collect(Collectors.toList());
    }


}
