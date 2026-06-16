package com.findpet.findpet_backend.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/*
 * DTO usado para receber os dados de cadastro e atualização de usuário.
 * Não possui ID, pois o identificador é gerado automaticamente pelo banco.
 */
@Data
public class UsuarioRequestDTO {

    /*
 * Validações aplicadas aos dados recebidos na requisição.
 */
    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Email(message = "Digite um email válido.")
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    private Long perfilId;
    

    public UsuarioRequestDTO() {
    }

    public UsuarioRequestDTO(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfilId = perfilId;
    }

}
