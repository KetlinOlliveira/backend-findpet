package com.findpet.findpet_backend.especie.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EspecieRequestDTO {

    @NotBlank(message = "O nome da espécie é obrigatório.")
    private String nome;
}
