package com.findpet.findpet_backend.cep.dto;

import lombok.Data;

/*
 * DTO que representa os dados retornados pela API externa de CEP.
 */
@Data
public class CepResponseDTO {

    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String service;
}