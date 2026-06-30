package com.findpet.findpet_backend.perfil.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PerfilRequestDTO {

    @NotBlank(message = "O nome do perfil é obrigatório.")
    @Size(min = 3, max = 50, message = "O nome do perfil deve ter entre 3 e 50 caracteres.")
    private String nome;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    private String descricao;

    public PerfilRequestDTO() {
    }

    public PerfilRequestDTO(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}