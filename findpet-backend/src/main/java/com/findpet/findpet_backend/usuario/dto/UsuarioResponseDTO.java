package com.findpet.findpet_backend.usuario.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private Boolean ativo;
    private String fotoUrl;
    private LocalDateTime dataCriacao;
    private Long perfilId;
    private String perfilNome;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(
            Long id,
            String nome,
            String email,
            Boolean ativo,
            LocalDateTime dataCriacao,
            Long perfilId,
            String perfilNome
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
        this.perfilId = perfilId;
        this.perfilNome = perfilNome;
    }
}