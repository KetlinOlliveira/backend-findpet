package com.findpet.findpet_backend.raca.dto;

import lombok.Data;

@Data
public class RacaResponseDTO {

    private Long id;
    private String nome;

    private Long especieId;
    private String especieNome;
}
