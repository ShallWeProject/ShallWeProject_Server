//package com.shallwe.domain.auth.application;
//
//import com.shallwe.domain.auth.domain.VerificationCode;
//import com.shallwe.domain.auth.domain.repository.VerificationCodeRepository;
//import lombok.RequiredArgsConstructor;
//import net.nurigo.sdk.NurigoApp;
//import net.nurigo.sdk.message.model.Message;
//import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
//import net.nurigo.sdk.message.response.SingleMessageSentResponse;
//import net.nurigo.sdk.message.service.DefaultMessageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.Random;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class CoolSMSService {
//
//    private final DefaultMessageService messageService;
//    private final VerificationCodeRepository verificationCodeRepository;
//
//    @Autowired
//    public CoolSMSService(VerificationCodeRepository verificationCodeRepository) {
//        this.verificationCodeRepository = verificationCodeRepository;
//        this.messageService = NurigoApp.INSTANCE.initialize("NCSXRHMDGUEFLVMT", "OFZDM0EKWCPU3QIDYYXTNZQGSATCBUGM", "https://api.coolsms.co.kr");
//    }
//
//    @Transactional
//    public SingleMessageSentResponse sendOne(String phoneNumber) {
//        Message message = new Message();
//        message.setFrom("01030239006");
//        message.setTo(phoneNumber);
//
//        String code = generateRandomCode();
//        message.setText("Shallwe 서비스 인증을 위한 인증번호입니다.\n" + code);
//
//        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(10);
//
//        VerificationCode verificationCode = VerificationCode.builder()
//                .phoneNumber(phoneNumber)
//                .code(code)
//                .expiryDate(expiryDate)
//                .build();
//
//        verificationCodeRepository.save(verificationCode);
//
//        SingleMessageSentResponse singleMessageSentResponse = this.messageService.sendOne(new SingleMessageSendingRequest(message));
//
//        return singleMessageSentResponse;
//    }
//
//    private String generateRandomCode() {
//        return String.format("%06d", new Random().nextInt(999999));
//    }
//
//}