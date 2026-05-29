package com.findpet.findpet_backend.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/*
 * Configuração responsável por disponibilizar o WebClient na aplicação.
 */
@Configuration
public class WebClientConfig {

    /*
     * Cria um WebClient apontando para a BrasilAPI.
     * Esse cliente será usado para consumir dados externos de CEP.
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://brasilapi.com.br/api")
                .build();
    }
}