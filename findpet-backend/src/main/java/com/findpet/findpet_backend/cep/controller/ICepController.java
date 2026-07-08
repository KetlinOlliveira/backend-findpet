package com.findpet.findpet_backend.cep.controller;

import com.findpet.findpet_backend.cep.dto.CepResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * Contrato REST do módulo de consulta de CEP.
 * As anotações de mapeamento HTTP e de documentação (Swagger) ficam aqui;
 * a implementação concreta só executa a lógica e monta as respostas.
 */
@Tag(name = "CEP", description = "Consulta de endereços via BrasilAPI a partir de um CEP.")
public interface ICepController {

    @Operation(summary = "Consultar um CEP na BrasilAPI")
    @GetMapping("/{cep}")
    ResponseEntity<CepResponseDTO> buscarCep(@PathVariable String cep);
}
