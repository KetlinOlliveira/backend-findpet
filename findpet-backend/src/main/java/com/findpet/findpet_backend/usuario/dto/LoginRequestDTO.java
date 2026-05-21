package com.findpet.findpet_backend.usuario.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    
    private String email;
    private String senha;

    public LoginRequestDTO() {
    }
    public LoginRequestDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

}
