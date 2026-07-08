package com.findpet.findpet_backend.raca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RacaRequestDTO {

    @NotBlank(message = "O nome da raça é obrigatório.")
    private String nome;

    @NotNull(message = "A espécie é obrigatória.")
    private Long especieId;
}
