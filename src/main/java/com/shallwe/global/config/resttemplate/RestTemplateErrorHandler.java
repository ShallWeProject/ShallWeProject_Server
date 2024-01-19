package com.shallwe.global.config.resttemplate;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(@NonNull final ClientHttpResponse response) throws IOException {
        final HttpStatus statusCode = (HttpStatus) response.getStatusCode();

        return !statusCode.is2xxSuccessful();
    }

    @Override
    public void handleError(@NonNull final ClientHttpResponse response) throws IOException {
        final String error = getErrorAsString(response);

        log.error("==========error response begin==========");
        log.error("Headers : {}", response.getHeaders());
        log.error("Response Status : {}", response.getStatusCode());
        log.error("Request Body : {}", error);
        log.error("==========error response end==========");
    }

    private String getErrorAsString(@NonNull final ClientHttpResponse response) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody()))) {
            return br.readLine();
        }
    }

}
