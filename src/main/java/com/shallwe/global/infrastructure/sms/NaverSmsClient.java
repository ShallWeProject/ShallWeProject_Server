package com.shallwe.global.infrastructure.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shallwe.domain.auth.domain.VerificationCode;
import com.shallwe.domain.auth.domain.repository.VerificationCodeRepository;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.user.domain.User;
import com.shallwe.global.infrastructure.sms.dto.MessageMapping;
import com.shallwe.domain.auth.dto.request.NaverCloudSmsReq;
import com.shallwe.domain.auth.dto.request.ValidVerificationCodeReq;
import com.shallwe.global.infrastructure.sms.dto.AlimTalkReq;
import com.shallwe.global.infrastructure.sms.exception.InvalidPhoneNumberException;
import com.shallwe.global.infrastructure.sms.exception.InvalidVerificationCodeException;
import com.shallwe.global.infrastructure.sms.exception.TimeOutException;
import com.shallwe.domain.auth.dto.response.SmsResponseDto;
import com.shallwe.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NaverSmsClient implements SmsClient {

    @Value("${sms.naver-cloud.sms-service-id}")
    private String SMS_SERVICE_ID;

    @Value("${sms.naver-cloud.biztalk-service-id}")
    private String BIZTALK_SERVICE_ID;

    @Value("${sms.naver-cloud.access-key}")
    private String ACCESS_KEY;

    @Value("${sms.naver-cloud.secret-key}")
    private String SECRET_KEY;

    @Value("${sms.naver-cloud.sender-phone}")
    private String PHONE_NUMBER;

    private final VerificationCodeRepository verificationCodeRepository;

    @Override
    @Transactional
    public SmsResponseDto send(String receivePhoneNumber) throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String url = "/sms/v2/services/" + SMS_SERVICE_ID + "/messages";
        String signature = makeSignature(timestamp, url);

        String code = generateRandomCode();

        MessageMapping messageMapping = MessageMapping.builder()
                .to(receivePhoneNumber)
                .content("[Shall We] 인증번호 [" + code + "]를 입력해주세요.").build();

        List<MessageMapping> messages = new ArrayList<>();
        messages.add(messageMapping);

        NaverCloudSmsReq naverCloudSmsReq = NaverCloudSmsReq.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(PHONE_NUMBER)
                .content(messageMapping.getContent())
                .messages(messages)
                .build();

        VerificationCode verificationCode = VerificationCode.builder()
                .phoneNumber(receivePhoneNumber)
                .code(code)
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .build();

        verificationCodeRepository.save(verificationCode);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(naverCloudSmsReq);

        RestClient restClient = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl("https://sens.apigw.ntruss.com/sms/v2/services")
                .build();

        return restClient.post().uri("/" + SMS_SERVICE_ID + "/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-ncp-apigw-timestamp", timestamp)
                .header("x-ncp-iam-access-key", ACCESS_KEY)
                .header("x-ncp-apigw-signature-v2", signature)
                .body(body)
                .retrieve()
                .body(SmsResponseDto.class);
    }

    public void sendApply(User receiver, ExperienceGift experienceGift, Reservation reservation) throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String url = "/alimtalk/v2/services/" + BIZTALK_SERVICE_ID + "/messages";
        String signature = makeSignature(timestamp, url);

        RestClient restClient = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl("https://sens.apigw.ntruss.com/alimtalk/v2")
                .defaultHeaders(header -> {
                    header.set("Content-Type", "application/json");
                    header.set("x-ncp-apigw-timestamp", timestamp);
                    header.set("x-ncp-iam-access-key", ACCESS_KEY);
                    header.set("x-ncp-apigw-signature-v2", signature);
                })
                .build();

        NumberFormat format = NumberFormat.getNumberInstance();
        String price = format.format(experienceGift.getPrice());
        String account = experienceGift.getShopOwner().getBankbook();
        String date = reservation.getDate().toString();
        String time = reservation.getTime().toString();
        String receiveUserName = receiver.getName();
        String productName = experienceGift.getTitle();
        String persons = reservation.getPersons().toString() + "명";

        List<MessageMapping> messages = new ArrayList<>();
        messages.add(MessageMapping.builder()
                .to(receiver.getPhoneNumber())
                .content("[셸위]\n" +
                        "예약이 접수되었습니다.\n" +
                        "아래 계좌로 입금이 확인되면 예약확정과 함께 초대장이 발송됩니다.\n" +
                        "\n" +
                        "\uD83D\uDCCC 금액: " + price + "원\n" +
                        "입금계좌: " + account + "\n" +
                        "\n" +
                        "예약날짜: " + date + "\n" +
                        "예약시간: " + time + "\n" +
                        "수취인: " + receiveUserName + "\n" +
                        "상품명: " + productName + "\n" +
                        "옵션: " + persons)
                .build());

        AlimTalkReq alimTalkReq = AlimTalkReq.builder()
                .plusFriendId("@shallwee")
                .templateCode("reservationApply")
                .messages(messages)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(alimTalkReq);

        restClient.post()
                .uri("/services/" + BIZTALK_SERVICE_ID + "/messages")
                .body(body)
                .retrieve()
                .body(SmsResponseDto.class);
    }

    public void sendInvitationAndConfirm(final Reservation reservation) throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String url = "/alimtalk/v2/services/" + BIZTALK_SERVICE_ID + "/messages";
        String signature = makeSignature(timestamp, url);

        RestClient restClient = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl("https://sens.apigw.ntruss.com/alimtalk/v2")
                .defaultHeaders(header -> {
                    header.set("Content-Type", "application/json");
                    header.set("x-ncp-apigw-timestamp", timestamp);
                    header.set("x-ncp-iam-access-key", ACCESS_KEY);
                    header.set("x-ncp-apigw-signature-v2", signature);
                })
                .build();

        String sendUserName = reservation.getSender().getName();
        String date = reservation.getDate().toString();
        String time = reservation.getTime().toString();
        String receiveUserName = reservation.getReceiver().getName();
        String productName = reservation.getExperienceGift().getTitle();
        String persons = reservation.getPersons().toString() + "명";

        List<MessageMapping> messages = new ArrayList<>();
        messages.add(MessageMapping.builder()
                .to(reservation.getReceiver().getPhoneNumber())
                .content("[셸위]\n" +
                        sendUserName + "님이 초대장을 보냈어요!\uD83C\uDF81\n" +
                        "\n" +
                        "예약날짜: " + date + "\n" +
                        "예약시간: " + time + "\n" +
                        "수취인: " + receiveUserName + "\n" +
                        "상품명: " + productName + "\n" +
                        "옵션: " + persons + "\n" +
                        "\n" +
                        "따뜻한 마음이 담긴 선물을\n" +
                        "지금 바로 셸위 어플에서 확인해보세요.\uD83E\uDD70")
                .build());
        messages.add(MessageMapping.builder()
                .to(reservation.getSender().getPhoneNumber())
                .content("[셸위]\n" +
                        "예약이 확정되었습니다.\n" +
                        receiveUserName + "님께 초대장이 전달되었습니다.\n" +
                        "\n" +
                        "셸위가 잊지못할 하루를 만들어드릴게요❤\uFE0F\n" +
                        "\n" +
                        "예약날짜: " + date + "\n" +
                        "예약시간: " + time + "\n" +
                        "수취인: " + receiveUserName + "\n" +
                        "상품명: " + productName + "\n" +
                        "옵션: " + persons)
                .build());

        AlimTalkReq alimTalkReq = AlimTalkReq.builder()
                .plusFriendId("@shallwee")
                .templateCode("invitation")
                .messages(messages)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(alimTalkReq);

        restClient.post()
                .uri("/services/" + BIZTALK_SERVICE_ID + "/messages")
                .body(body)
                .retrieve()
                .body(SmsResponseDto.class);

        messages.remove(0);
        alimTalkReq = AlimTalkReq.builder()
                .plusFriendId("@shallwee")
                .templateCode("reservationConfirmed")
                .messages(messages)
                .build();

        body = objectMapper.writeValueAsString(alimTalkReq);
        restClient.post()
                .uri("/services/" + BIZTALK_SERVICE_ID + "/messages")
                .body(body)
                .retrieve()
                .body(SmsResponseDto.class);
    }

    public void sendCancel(Reservation reservation) throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String url = "/alimtalk/v2/services/" + BIZTALK_SERVICE_ID + "/messages";
        String signature = makeSignature(timestamp, url);

        RestClient restClient = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl("https://sens.apigw.ntruss.com/alimtalk/v2")
                .defaultHeaders(header -> {
                    header.set("Content-Type", "application/json");
                    header.set("x-ncp-apigw-timestamp", timestamp);
                    header.set("x-ncp-iam-access-key", ACCESS_KEY);
                    header.set("x-ncp-apigw-signature-v2", signature);
                })
                .build();

        String date = reservation.getDate().toString();
        String time = reservation.getTime().toString();
        String receiveUserName = reservation.getReceiver().getName();
        String productName = reservation.getExperienceGift().getTitle();
        String persons = reservation.getPersons().toString() + "명";

        List<MessageMapping> messages = new ArrayList<>();
        messages.add(MessageMapping.builder()
                .to(reservation.getSender().getPhoneNumber())
                .content("[셸위]\n" +
                        "예약이 취소되었습니다\n" +
                        "문자로 안내드린 절차에 따라 환불절차 진행 부탁드립니다\n" +
                        "\n" +
                        "예약날짜: " + date + "\n" +
                        "예약시간: " + time + "\n" +
                        "수취인: " + receiveUserName + "\n" +
                        "상품명: " + productName + "\n" +
                        "옵션: "+ persons)
                .build());

        AlimTalkReq alimTalkReq = AlimTalkReq.builder()
                .plusFriendId("@shallwee")
                .templateCode("cancelReservation")
                .messages(messages)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(alimTalkReq);

        restClient.post()
                .uri("/services/" + BIZTALK_SERVICE_ID + "/messages")
                .body(body)
                .retrieve()
                .body(SmsResponseDto.class);
    }

    @Transactional
    public Message validVerificationCode(ValidVerificationCodeReq validVerificationCodeReq) {
        LocalDateTime time = LocalDateTime.now();

        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(validVerificationCodeReq.getPhoneNumber())
                .orElseThrow(InvalidPhoneNumberException::new);

        //시간 만료시 재발급 요청
        if (time.isAfter(verificationCode.getExpiryDate())) {
            verificationCodeRepository.delete(verificationCode);
            throw new TimeOutException();
        }

        //인증번호 불일치시 예외발생
        if (!verificationCode.getCode().equals(validVerificationCodeReq.getVerificationCode())) {
            throw new InvalidVerificationCodeException();
        }

        verificationCodeRepository.delete(verificationCode);

        return Message.builder()
                .message("인증이 완료되었습니다.")
                .build();
    }

    public String makeSignature(String timestamp, String url) throws Exception {
        String space = " ";
        String newLine = "\n";
        String method = "POST";

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(ACCESS_KEY)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(rawHmac);
    }

    public String generateRandomCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

}