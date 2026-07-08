package com.findpet.findpet_backend.adocao.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdocaoRequestDTO {

    @NotNull(message = "O usuário é obrigatório.")
    private Long usuarioId;

    @NotNull(message = "O animal é obrigatório.")
    private Long animalId;

    private String observacao;
}
