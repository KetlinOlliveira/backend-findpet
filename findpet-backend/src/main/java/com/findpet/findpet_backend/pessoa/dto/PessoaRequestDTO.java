 package com.findpet.findpet_backend.pessoa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PessoaRequestDTO {

    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;

    private String telefone;

    private LocalDate dataNascimento;

    @NotNull(message = "O usuário é obrigatório.")
    private Long usuarioId;

    @NotNull(message = "O endereço é obrigatório.")
    private Long enderecoId;

    public PessoaRequestDTO() {
    }
} 