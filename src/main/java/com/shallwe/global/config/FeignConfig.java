package com.shallwe.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.shallwe.global.infrastructure.feign")
public class FeignConfig {
}
