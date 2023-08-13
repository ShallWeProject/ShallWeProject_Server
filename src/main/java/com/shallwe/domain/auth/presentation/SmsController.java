package com.shallwe.domain.auth.presentation;

import com.shallwe.domain.auth.application.SmsService;
import com.shallwe.domain.auth.dto.NaverCloudSmsReq;
import com.shallwe.domain.auth.dto.SmsReq;
import com.shallwe.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/send-one")
    public ResponseCustom<?> sendOne(
            @RequestBody SmsReq smsReq
            ) throws Exception {
        return ResponseCustom.OK(smsService.sendOne(smsReq.getPhoneNumber()));
    }

}
