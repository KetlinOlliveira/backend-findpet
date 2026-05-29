package com.findpet.findpet_backend.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

// Representa um erro específico de um campo enviado na requisição.
@Data
@AllArgsConstructor

public class FieldErrorDTO {
    private String campo;
    private String mensagem;
}