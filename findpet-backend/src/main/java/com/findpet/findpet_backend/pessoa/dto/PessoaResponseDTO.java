package com.findpet.findpet_backend.pessoa.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PessoaResponseDTO {

    private Long id;
    private String cpf;
    private String telefone;
    private LocalDate dataNascimento;

    private Long usuarioId;
    private String usuarioNome;
    private String usuarioEmail;

    private Long enderecoId;
    private String cep;
    private String cidade;
    private String estado;

    public PessoaResponseDTO() {
    }
}
