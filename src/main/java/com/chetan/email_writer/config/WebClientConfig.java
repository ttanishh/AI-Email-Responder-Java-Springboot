package com.chetan.email_writer.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Data
public class WebClientConfig {

    @Bean
    public WebClient webClient() {

        return WebClient.builder().build();
    }
}
