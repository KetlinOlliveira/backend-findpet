package com.findpet.findpet_backend.cep.service;

import com.findpet.findpet_backend.cep.dto.CepResponseDTO;
import com.findpet.findpet_backend.infrastructure.exception.BusinessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/*
 * Service responsável por consumir a API externa de CEP usando WebClient.
 */
@Service
public class CepService {

    private final WebClient webClient;

    public CepService(WebClient webClient) {
        this.webClient = webClient;
    }

    /*
     * Busca os dados de um CEP na BrasilAPI.
     * O WebClient faz a requisição HTTP e converte a resposta para CepResponseDTO.
     */
    public CepResponseDTO buscarCep(String cep) {
        return webClient
                .get()
                .uri("/cep/v1/{cep}", cep)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new BusinessException("CEP não encontrado."))
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new BusinessException("Erro ao consultar a API externa de CEP."))
                )
                .bodyToMono(CepResponseDTO.class)
                .block();
    }
}