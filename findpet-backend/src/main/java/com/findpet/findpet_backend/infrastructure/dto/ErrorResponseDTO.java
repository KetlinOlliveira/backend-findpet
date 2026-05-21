package com.findpet.findpet_backend.infrastructure.dto;

import java.time.LocalDateTime;

import lombok.Data;

/*
* Trata qualquer erro inesperado não capturado pelos outros métodos.
*/
@Data
public class ErrorResponseDTO {

    private LocalDateTime timestamp;
    private Integer status;
    private String erro;
    private String mensagem;
    private String caminho;

    public ErrorResponseDTO() {
    }


    /*
 * Cria uma resposta de erro com o horário atual da ocorrência.
 */
    public ErrorResponseDTO(Integer status, String erro, String mensagem, String caminho) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.caminho = caminho;
    }
}