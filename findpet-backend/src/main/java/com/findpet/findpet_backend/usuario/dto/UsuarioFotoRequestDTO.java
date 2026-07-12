package com.findpet.findpet_backend.usuario.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

/*
 * DTO usado só para trocar a foto de perfil do usuário.
 * Separado do UsuarioRequestDTO porque esse último exige nome/email/senha —
 * não faz sentido pedir a senha de novo só para trocar uma foto.
 */
@Data
public class UsuarioFotoRequestDTO {

    @Size(max = 3_500_000, message = "A foto é muito grande (limite aproximado de 2,5MB).")
    private String fotoUrl;
}
