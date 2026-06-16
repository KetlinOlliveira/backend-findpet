package com.findpet.findpet_backend.endereco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    
}