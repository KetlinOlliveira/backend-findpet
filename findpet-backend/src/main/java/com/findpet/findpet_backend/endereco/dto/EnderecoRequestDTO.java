package com.findpet.findpet_backend.endereco.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnderecoRequestDTO {

    @NotBlank(message = "O CEP é obrigatório.")
    private String cep;

    @NotBlank(message = "O estado é obrigatório.")
    private String estado;

    @NotBlank(message = "A cidade é obrigatória.")
    private String cidade;

    private String bairro;
    private String rua;
    private String numero;
    private String complemento;

    public EnderecoRequestDTO() {
    }
} 
