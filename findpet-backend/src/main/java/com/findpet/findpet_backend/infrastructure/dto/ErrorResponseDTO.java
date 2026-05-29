package com.findpet.findpet_backend.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.findpet.findpet_backend.infrastructure.exception.FieldErrorDTO;

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
    private List<FieldErrorDTO> campos;

    public ErrorResponseDTO() {
    }


    /*
 * Cria uma resposta de erro com o horário atual da ocorrência
 */
    public ErrorResponseDTO(Integer status, String erro, String mensagem, String caminho) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.caminho = caminho;
    }

    /*
     * Cria uma resposta de erro com a lista dos campos inválidos
     */
    public ErrorResponseDTO(Integer status, String erro, String mensagem, String caminho, List<FieldErrorDTO> campos) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.caminho = caminho;
        this.campos = campos;
    }
}