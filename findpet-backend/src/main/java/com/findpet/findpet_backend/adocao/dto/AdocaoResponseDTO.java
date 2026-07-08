package com.findpet.findpet_backend.adocao.dto;

import com.findpet.findpet_backend.adocao.anums.StatusAdocao;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdocaoResponseDTO {

    private Long id;
    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataConclusao;
    private StatusAdocao status;
    private String observacao;

    private Long usuarioId;
    private String usuarioNome;

    private Long animalId;
    private String animalNome;
}
