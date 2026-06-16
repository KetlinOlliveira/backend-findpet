package com.findpet.findpet_backend.endereco.dto;

import lombok.Data;

@Data
public class EnderecoResponseDTO {

    private Long id;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String complemento;

    public EnderecoResponseDTO() {
    }
}