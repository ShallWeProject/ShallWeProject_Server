//package com.shallwe.domain.auth.presentation;
//
//import com.shallwe.domain.auth.application.CoolSMSService;
//import com.shallwe.domain.auth.dto.NaverCloudSmsReq;
//import com.shallwe.global.payload.ResponseCustom;
//import lombok.RequiredArgsConstructor;
//import net.nurigo.sdk.message.response.SingleMessageSentResponse;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/auth")
//public class CoolSMSController {
//
//    private final CoolSMSService coolSMSService;
//
//    @PostMapping("/send-one")
//    public ResponseCustom<SingleMessageSentResponse> sendOne(
//            @RequestBody NaverCloudSmsReq naverCloudSmsReq
//    ) {
//        return ResponseCustom.OK(coolSMSService.sendOne(naverCloudSmsReq.getPhoneNumber()));
//    }
//
//}
