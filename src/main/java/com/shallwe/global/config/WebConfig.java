package com.shallwe.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // url 패턴
                .allowedOriginPatterns("*")
                // todo: server url 생성 시 변경 필요
//                .allowedOrigins("url:8080", "http://localhost:8080")
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PATCH.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name(),
                        HttpMethod.HEAD.name(), HttpMethod.TRACE.name(), HttpMethod.OPTIONS.name()) // 허용 method
                .allowedHeaders("Authorization", "Content-Type"); // 허용 header
                .allowCredentials(true);
    }
}
