package com.findpet.findpet_backend.usuario.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.findpet.findpet_backend.perfil.model.Perfil;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Entidade que representa a tabela de usuários no banco de dados.
 * Cada objeto desta classe será persistido como um registro na tabela usuarios.
 */

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    /*
    * Identificador único do usuário.
    * O valor é gerado automaticamente pelo banco de dados.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    * Dados básicos do usuário utilizados para cadastro e autenticação.
    */
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Email(message = "Digite um email válido.")
    @NotBlank(message = "O email é obrigatório.")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    private Boolean ativo = true;

    @Column(name = "foto_url", columnDefinition = "TEXT")
    private String fotoUrl;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

}