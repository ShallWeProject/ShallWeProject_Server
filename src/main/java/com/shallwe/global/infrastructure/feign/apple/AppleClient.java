package com.shallwe.global.infrastructure.feign.apple;

import com.shallwe.global.config.feign.FeignConfig;
import com.shallwe.global.infrastructure.feign.apple.dto.ApplePublicKeyRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth", configuration = FeignConfig.class)
public interface AppleClient {

    @GetMapping(value = "/keys")
    ApplePublicKeyRes getAppleAuthPublicKey();

}
