//package com.shallwe.domain.auth.application;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.shallwe.domain.auth.domain.VerificationCode;
//import com.shallwe.domain.auth.domain.repository.VerificationCodeRepository;
//import com.shallwe.domain.auth.dto.*;
//import com.shallwe.global.infrastructure.sms.exception.InvalidPhoneNumberException;
//import com.shallwe.global.infrastructure.sms.exception.InvalidVerificationCodeException;
//import com.shallwe.global.infrastructure.sms.exception.TimeOutException;
//import com.shallwe.global.payload.Message;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestTemplate;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.net.URI;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.List;
//import java.util.Random;
//
//@Service
//@RequiredArgsConstructor
//@ConfigurationProperties(prefix = "naver-cloud.sms")
//@Transactional(readOnly = true)
//public class SmsService {
//
//    @Value("${naver-cloud.sms.serviceId}")
//    private String SERVICE_ID;
//    @Value("${naver-cloud.sms.accessKey}")
//    private String ACCESS_KEY;
//    @Value("${naver-cloud.sms.secretKey}")
//    private String SECRET_KEY;
//    @Value("${naver-cloud.sms.senderPhone}")
//    private String PHONE_NUMBER;
//
//    private final VerificationCodeRepository verificationCodeRepository;
//
//    @Transactional
//    public SmsResponseDto sendOne(String phoneNumber) throws Exception {
//        String code = generateRandomCode();
//        MessageDTO messageDTO = MessageDTO.builder()
//                .to(phoneNumber)
//                .content("[Shall We] 인증번호 [" + code + "]를 입력해주세요.").build();
//
//        List<MessageDTO> messages = new ArrayList<>();
//        messages.add(messageDTO);
//
//        NaverCloudSmsReq naverCloudSmsReq = NaverCloudSmsReq.builder()
//                .type("SMS")
//                .contentType("COMM")
//                .countryCode("82")
//                .from(PHONE_NUMBER)
//                .content(messageDTO.getContent())
//                .messages(messages)
//                .build();
//
//        VerificationCode verificationCode = VerificationCode.builder()
//                .phoneNumber(phoneNumber)
//                .code(code)
//                .expiryDate(LocalDateTime.now().plusMinutes(10))
//                .build();
//
//        verificationCodeRepository.save(verificationCode);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String body = objectMapper.writeValueAsString(naverCloudSmsReq);
//        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);
//
//        SmsResponseDto response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + SERVICE_ID + "/messages"), httpBody, SmsResponseDto.class);
//
//        return response;
//    }
//
//    private String generateRandomCode() {
//        return String.format("%06d", new Random().nextInt(999999));
//    }
//
//    private String makeSignature(String timestamp) throws Exception {
//        String space = " ";
//        String newLine = "\n";
//        String method = "POST";
//        String url = "/sms/v2/services/" + SERVICE_ID + "/messages";
//
//        String message = new StringBuilder()
//                .append(method)
//                .append(space)
//                .append(url)
//                .append(newLine)
//                .append(timestamp)
//                .append(newLine)
//                .append(ACCESS_KEY)
//                .toString();
//
//        SecretKeySpec signingKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//        Mac mac = Mac.getInstance("HmacSHA256");
//        mac.init(signingKey);
//
//        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
//        return Base64.getEncoder().encodeToString(rawHmac);
//    }
//
//    @Transactional
//    public Message validVerificationCode(ValidVerificationCodeReq validVerificationCodeReq) {
//        LocalDateTime time = LocalDateTime.now();
//
//        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(validVerificationCodeReq.getPhoneNumber())
//                .orElseThrow(InvalidPhoneNumberException::new);
//
//        //시간 만료시 재발급 요청
//        if (time.isAfter(verificationCode.getExpiryDate())) {
//            verificationCodeRepository.delete(verificationCode);
//            throw new TimeOutException();
//        }
//
//        //인증번호 불일치시 예외발생
//        if (!verificationCode.getCode().equals(validVerificationCodeReq.getVerificationCode())) {
//            throw new InvalidVerificationCodeException();
//        }
//
//        verificationCodeRepository.delete(verificationCode);
//
//        return Message.builder()
//                .message("인증이 완료되었습니다.")
//                .build();
//    }
//
//}
