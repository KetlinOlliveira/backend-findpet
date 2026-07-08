package com.findpet.findpet_backend.log_sistema.dto;

import com.findpet.findpet_backend.log_sistema.enums.AcaoLogSistema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogSistemaRequestDTO {

    @NotNull(message = "A ação é obrigatória.")
    private AcaoLogSistema acao;

    private String descricao;

    @NotNull(message = "O usuário é obrigatório.")
    private Long usuarioId;
}
