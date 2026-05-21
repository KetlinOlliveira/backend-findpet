package com.findpet.findpet_backend.usuario.dto;

import lombok.Data;


/*
 * DTO usado para retornar os dados públicos do usuário.
 * A senha não é incluída para evitar exposição de dados sensíveis.
 */
@Data
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }
    
}
