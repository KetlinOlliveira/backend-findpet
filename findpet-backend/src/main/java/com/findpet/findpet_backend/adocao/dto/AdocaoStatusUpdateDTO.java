package com.findpet.findpet_backend.adocao.dto;

import com.findpet.findpet_backend.adocao.anums.StatusAdocao;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdocaoStatusUpdateDTO {

    @NotNull(message = "O novo status da adoção é obrigatório.")
    private StatusAdocao status;
}
