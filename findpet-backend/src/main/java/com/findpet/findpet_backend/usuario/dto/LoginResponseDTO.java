package com.findpet.findpet_backend.usuario.dto;

import lombok.Data;

/*
 * Resposta devolvida após um login bem-sucedido: o token JWT que o
 * frontend deve enviar nas próximas requisições (header
 * "Authorization: Bearer <token>") e os dados públicos do usuário logado.
 */
@Data
public class LoginResponseDTO {

    private String token;
    private String tipo;
    private UsuarioResponseDTO usuario;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, UsuarioResponseDTO usuario) {
        this.token = token;
        this.tipo = "Bearer";
        this.usuario = usuario;
    }
}
