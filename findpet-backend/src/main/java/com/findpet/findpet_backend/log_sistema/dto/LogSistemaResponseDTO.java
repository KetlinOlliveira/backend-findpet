package com.findpet.findpet_backend.log_sistema.dto;

import com.findpet.findpet_backend.log_sistema.enums.AcaoLogSistema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogSistemaResponseDTO {

    private Long id;
    private AcaoLogSistema acao;
    private String descricao;
    private LocalDateTime dataHora;

    private Long usuarioId;
    private String usuarioNome;
}
