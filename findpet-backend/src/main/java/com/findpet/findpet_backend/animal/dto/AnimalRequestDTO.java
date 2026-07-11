package com.findpet.findpet_backend.animal.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AnimalRequestDTO {

    @NotBlank(message = "O nome do animal é obrigatório.")
    private String nome;

    @Min(value = 0, message = "A idade não pode ser negativa.")
    private Integer idade;

    private String porte;

    private String sexo;

    private String descricao;

    @Size(max = 3_500_000, message = "A foto é muito grande (limite aproximado de 2,5MB).")
    private String fotoUrl;

    @NotNull(message = "O usuário responsável é obrigatório.")
    private Long usuarioId;

    @NotNull(message = "A espécie é obrigatória.")
    private Long especieId;

    @NotNull(message = "A raça é obrigatória.")
    private Long racaId;
}
