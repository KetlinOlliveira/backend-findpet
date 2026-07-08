package com.findpet.findpet_backend.raca.controller;

import com.findpet.findpet_backend.raca.dto.RacaRequestDTO;
import com.findpet.findpet_backend.raca.dto.RacaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * Contrato REST do módulo de raças.
 * As anotações de mapeamento HTTP e de documentação (Swagger) ficam aqui;
 * a implementação concreta só executa a lógica e monta as respostas.
 */
@Tag(name = "Raças", description = "Cadastro e consulta das raças, vinculadas a uma espécie.")
public interface IRacaController {

    @Operation(summary = "Cadastrar uma nova raça vinculada a uma espécie")
    @PostMapping
    ResponseEntity<RacaResponseDTO> cadastrar(@Valid @RequestBody RacaRequestDTO racaRequestDTO);

    @Operation(summary = "Listar raças cadastradas, de forma paginada")
    @GetMapping
    ResponseEntity<Page<RacaResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable
    );

    @Operation(summary = "Buscar uma raça pelo ID")
    @GetMapping("/{id}")
    ResponseEntity<RacaResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Atualizar os dados de uma raça existente")
    @PutMapping("/{id}")
    ResponseEntity<RacaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RacaRequestDTO racaRequestDTO
    );

    @Operation(summary = "Excluir uma raça (não permitido se houver animais vinculados)")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Long id);
}
