package com.nexbuy.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {

        ClientHttpRequestInterceptor interceptor =
                (request, body, execution) -> {

                    ServletRequestAttributes attributes =
                            (ServletRequestAttributes)
                                    RequestContextHolder
                                            .getRequestAttributes();

                    if (attributes != null) {

                        String authorization =
                                attributes.getRequest()
                                        .getHeader("Authorization");

                        if (authorization != null) {

                            request.getHeaders()
                                    .set("Authorization",
                                            authorization);
                        }
                    }

                    return execution.execute(request, body);
                };

        return RestClient.builder()
                .requestInterceptor(interceptor)
                .build();
    }
}