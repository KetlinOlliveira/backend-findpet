package com.findpet.findpet_backend.usuario.dto;

import lombok.Data;

/*
 * DTO usado para receber os dados necessários para login.
 * Contém apenas email e senha.
 */
@Data
public class LoginRequestDTO {
    

/*
 * Validações dos campos obrigatórios para autenticação.
 */
    private String email;
    private String senha;

    public LoginRequestDTO() {
    }
    public LoginRequestDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

}
