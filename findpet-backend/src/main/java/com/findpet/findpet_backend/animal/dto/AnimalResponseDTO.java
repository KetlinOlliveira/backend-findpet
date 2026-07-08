package com.findpet.findpet_backend.animal.dto;


import com.findpet.findpet_backend.animal.enums.StatusAnimal;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnimalResponseDTO {

    private Long id;
    private String nome;
    private Integer idade;
    private String porte;
    private String sexo;

    private String descricao;
    private StatusAnimal status;
    private String fotoUrl;
    private LocalDateTime dataCadastro;

    private Long usuarioId;
    private String usuarioNome;

    private Long especieId;
    private String especieNome;

    private Long racaId;
    private String racaNome;
}