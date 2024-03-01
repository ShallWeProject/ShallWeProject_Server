package com.shallwe.global.infrastructure.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shallwe.domain.auth.domain.VerificationCode;
import com.shallwe.domain.auth.domain.repository.VerificationCodeRepository;
import com.shallwe.domain.auth.dto.MessageMapping;
import com.shallwe.domain.auth.dto.request.NaverCloudSmsReq;
import com.shallwe.domain.auth.dto.request.ValidVerificationCodeReq;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NaverSmsClient implements SmsClient {

    @Value("${sms.naver-cloud.service-id}")
    private String SERVICE_ID;

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
        String signature = makeSignature(timestamp);

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

        return restClient.post().uri("/" + SERVICE_ID + "/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-ncp-apigw-timestamp", timestamp)
                .header("x-ncp-iam-access-key", ACCESS_KEY)
                .header("x-ncp-apigw-signature-v2", signature)
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

    private String makeSignature(String timestamp) throws Exception {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/" + SERVICE_ID + "/messages";

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

    private String generateRandomCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

}