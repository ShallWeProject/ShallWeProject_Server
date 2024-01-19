package com.shallwe.global.config.resttemplate;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RestTemplateClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @NonNull
    @Override
    public ClientHttpResponse intercept(@NonNull final HttpRequest request,
                                        final byte @NonNull [] body, @NonNull ClientHttpRequestExecution execution)
            throws IOException {
        final ClientHttpResponse response = execution.execute(request, body);

        loggingResponse(response);
        loggingRequest(request, body);
        return execution.execute(request, body);
    }

    private void loggingRequest(final HttpRequest request, byte[] body) {
        log.info("===========================request begin================================================");
        log.info("Headers : {}", request.getHeaders());
        log.info("Request Method : {}", request.getMethod());
        log.info("Request URI : {}", request.getURI());
        log.info("Request Body : {}",
                body.length == 0 ? null : new String(body, StandardCharsets.UTF_8));
        log.info("==========================request end================================================");
    }

    private void loggingResponse(ClientHttpResponse response) throws IOException {
        final String body = getBody(response);

        log.info("============================response begin==========================================");
        log.info("Headers : {}", response.getHeaders());
        log.info("Status code : {}", response.getStatusCode());
        log.info("Response Body : {}", body);
        log.info("============================response end============================================");
    }

    private String getBody(@NonNull final ClientHttpResponse response) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody()))) {
            return br.readLine();
        }
    }

}
