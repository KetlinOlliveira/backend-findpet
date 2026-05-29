package com.findpet.findpet_backend.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    
    @Email(message = "Digite um email válido.")
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    public LoginRequestDTO() {
    }
    public LoginRequestDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

}
