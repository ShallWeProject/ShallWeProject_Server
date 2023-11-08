package com.shallwe.domain.experiencegift.application;

import com.shallwe.domain.experiencegift.domain.*;
import com.shallwe.domain.experiencegift.domain.repository.*;
import com.shallwe.domain.experiencegift.dto.request.AdminExperienceReq;
import com.shallwe.domain.experiencegift.dto.response.*;
import com.shallwe.domain.experiencegift.exception.ExperienceGiftNotFoundException;
import com.shallwe.domain.experiencegift.dto.request.ExperienceReq;
import com.shallwe.domain.experiencegift.exception.*;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.shopowner.domain.repository.ShopOwnerRepository;
import com.shallwe.domain.shopowner.exception.InvalidPhoneNumberException;
import com.shallwe.domain.shopowner.exception.InvalidShopOwnerException;
import com.shallwe.domain.user.domain.repository.UserRepository;
import com.shallwe.domain.user.exception.InvalidUserException;
import com.shallwe.global.config.security.token.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    private final ShopOwnerRepository shopOwnerRepository;
    private final ReservationRepository reservationRepository;


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
    public void registerExperienceGift(UserPrincipal userPrincipal, AdminExperienceReq adminExperienceReq) {
        ShopOwner shopOwner = shopOwnerRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidUserException::new);

        //Subtitle이 db에 저장되어 있는 값이 없으면 생성
        Subtitle subtitle = subtitleRepository.findByTitle(adminExperienceReq.getSubtitle())
                //null은 새로운 Subtitle엔티티가 저장될 때 데이터베이스가 새로운 IDFMF 생성하게 해줌
                .orElseGet(() -> subtitleRepository.save(new Subtitle(null,adminExperienceReq.getSubtitle())));

        ExpCategory expCategory = null;
        SttCategory sttCategory = null;

        // 경험 카테고리가 주어졌는지 확인하고 처리
        if (StringUtils.hasText(adminExperienceReq.getExpCategory())) {
            expCategory = expCategoryRepository.findByExpCategory(adminExperienceReq.getExpCategory())
                    .orElseThrow(ExpCategoryAlreadyExist::new);
        }

        // 상황 카테고리가 주어졌는지 확인하고 처리
        if (StringUtils.hasText(adminExperienceReq.getSttCategory())) {
            sttCategory = sttCategoryRepository.findBySttCategory(adminExperienceReq.getSttCategory())
                    .orElseThrow(SttCategoryAlreadyExist::new);
        }

        // 두 카테고리가 동시에 주어진 경우 오류 처리
        if (expCategory != null && sttCategory != null) {
            throw new ChooseOnlyOneCategory();
        }

        ExperienceGift experienceGift =experienceGiftRepository.save(ExperienceGift.toDto(adminExperienceReq, subtitle, expCategory, sttCategory, shopOwner));

        List<Explanation> explanations = adminExperienceReq.getExplanation().stream()
                .map(explanationReq -> Explanation.toDto(explanationReq, experienceGift))
                .collect(Collectors.toList());

        explanationRepository.saveAll(explanations);

    }

    @Override
    public AdminMainRes mainAdminExperienceGift(UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();

        ShopOwner shopOwner = shopOwnerRepository.findById(userId)
                .orElseThrow(InvalidPhoneNumberException::new);

        LocalDate currentDate = LocalDate.now();

        Long bookedReservationsCount = reservationRepository.countByExperienceGift_ShopOwnerAndReservationStatus(shopOwner, ReservationStatus.WAITING);

        return AdminMainRes.toDto(currentDate, bookedReservationsCount);

    public List<AdminExperienceRes> getExperienceGift(UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();

        ShopOwner shopOwner = shopOwnerRepository.findById(userId)
                .orElseThrow(InvalidShopOwnerException::new);

        List<ExperienceGift> experienceGifts = experienceGiftRepository.findByShopOwnerId(shopOwner.getId());

        return experienceGifts.stream()
                .map(AdminExperienceRes::toDto)
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
