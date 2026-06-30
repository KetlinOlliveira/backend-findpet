package com.findpet.findpet_backend.perfil.dto;
import lombok.Data;

@Data
public class PerfilResponseDTO {

    private Long id;
    private String nome;
    private String descricao;

    public PerfilResponseDTO() {
    }

    public PerfilResponseDTO(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }
} 
