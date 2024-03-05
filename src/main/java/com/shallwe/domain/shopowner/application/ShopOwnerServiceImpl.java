package com.shallwe.domain.shopowner.application;


import static com.shallwe.domain.reservation.domain.ReservationStatus.BOOKED;
import static com.shallwe.domain.reservation.domain.ReservationStatus.CONFIRMED;
import static com.shallwe.domain.reservation.domain.ReservationStatus.WAITING;

import com.shallwe.domain.auth.domain.Token;
import com.shallwe.domain.auth.domain.repository.TokenRepository;
import com.shallwe.domain.common.Status;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.repository.ExperienceGiftRepository;
import com.shallwe.domain.experiencegift.exception.ExperienceGiftNotFoundException;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.reservation.dto.response.ValidTimeSlotRes;
import com.shallwe.domain.reservation.exception.InvalidAvailableTimeException;
import com.shallwe.domain.reservation.exception.InvalidReservationException;
import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.shopowner.domain.repository.ShopOwnerRepository;
import com.shallwe.domain.shopowner.dto.ShopOwnerIdentificationReq;
import com.shallwe.domain.shopowner.exception.InvalidShopOwnerException;
import com.shallwe.domain.shopowner.exception.NotReservedException;
import com.shallwe.domain.shopowner.exception.ShopOwnerExperienceGiftMismatchException;
import com.shallwe.domain.user.exception.InvalidTokenException;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.infrastructure.sms.NaverSmsClient;
import com.shallwe.global.payload.Message;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;

import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ShopOwnerServiceImpl implements ShopOwnerService {

    private final NaverSmsClient naverSmsClient;
    private final ShopOwnerRepository shopOwnerRepository;
    private final TokenRepository tokenRepository;
    private final ReservationRepository reservationRepository;
    private final ExperienceGiftRepository experienceGiftRepository;

    @Override
    @Transactional
    public Message deleteCurrentShopOwner(UserPrincipal userPrincipal) {
        ShopOwner shopOwner = shopOwnerRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidShopOwnerException::new);
        Token token = tokenRepository.findByUserEmail(userPrincipal.getEmail())
                .orElseThrow(InvalidTokenException::new);

        shopOwner.updateStatus(Status.DELETE);
        tokenRepository.delete(token);

        return Message.builder()
                .message("사장 탈퇴가 완료되었습니다.")
                .build();
    }

    @Override
    public List<ValidTimeSlotRes> getShopOwnerReservation(UserPrincipal userPrincipal,
                                                          Long giftId) {
        ExperienceGift experienceGift = experienceGiftRepository.findById(giftId)
                .orElseThrow(ExperienceGiftNotFoundException::new);
        List<Reservation> reservationList = reservationRepository.findAllByExperienceGift(
                experienceGift).orElseThrow(InvalidAvailableTimeException::new);

        return reservationList.stream().map(reservation -> ValidTimeSlotRes.builder()
                .reservationId(reservation.getId())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .build()).toList();
    }

    @Override
    @Transactional
    public Message confirmPayment(UserPrincipal userPrincipal, Long reservationId) throws Exception {
        ShopOwner shopOwner = shopOwnerRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidShopOwnerException::new);

        Reservation reservation = reservationRepository.findReservationById(reservationId).orElseThrow(
                InvalidReservationException::new);

        if(!Objects.equals(reservation.getExperienceGift().getShopOwner().getId(), shopOwner.getId())) // 경험 선물과 사장님이 일치하지 않을 경우
            throw new ShopOwnerExperienceGiftMismatchException();

        if (!reservation.getReservationStatus().equals(BOOKED)) // 예약 상태가 BOOKED가 아닐 경우
            throw new NotReservedException();

        reservation.updateStatus(CONFIRMED);
        naverSmsClient.sendInvitationAndConfirm(reservation.getSender(), reservation.getReceiver(), reservation.getExperienceGift(), reservation);

        return Message.builder()
                .message("예약이 확정되었습니다.")
                .build();
    }

    @Override
    @Transactional
    public Message uploadShopOwnerIdentification(
            ShopOwnerIdentificationReq shopOwnerIdentificationReq, UserPrincipal userPrincipal) {
        ShopOwner shopOwner = shopOwnerRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidShopOwnerException::new);

        shopOwner.updateIdentification(
                AwsS3ImageUrlUtil.toUrl(shopOwnerIdentificationReq.getIdentification()));
        shopOwner.updateBusinessRegistration(
                AwsS3ImageUrlUtil.toUrl(shopOwnerIdentificationReq.getBusinessRegistration()));
        shopOwner.updateBankbook(AwsS3ImageUrlUtil.toUrl(shopOwnerIdentificationReq.getBankbook()));

        return Message.builder()
                .message("사장님 신분증/사업자등록증/통장사본 등록이 완료되었습니다.")
                .build();
    }

}
