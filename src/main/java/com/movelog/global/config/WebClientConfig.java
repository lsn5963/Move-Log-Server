package com.movelog.global.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${chatgpt.url}")
    private String gptUrl;

    @Bean
    public WebClient gptWebClient() {
        return createWebClient(gptUrl);
    }

    private WebClient createWebClient(String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(1000 * 1024 * 1024))
                .build();
    }
}
