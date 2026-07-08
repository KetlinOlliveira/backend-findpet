package com.findpet.findpet_backend.especie.controller;

import com.findpet.findpet_backend.especie.dto.EspecieRequestDTO;
import com.findpet.findpet_backend.especie.dto.EspecieResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * Contrato REST do módulo de espécies.
 * As anotações de mapeamento HTTP e de documentação (Swagger) ficam aqui;
 * a implementação concreta só executa a lógica e monta as respostas.
 */
@Tag(name = "Espécies", description = "Cadastro e consulta das espécies de animais (ex: Cachorro, Gato).")
public interface IEspecieController {

    @Operation(summary = "Cadastrar uma nova espécie")
    @PostMapping
    ResponseEntity<EspecieResponseDTO> cadastrar(@Valid @RequestBody EspecieRequestDTO especieRequestDTO);

    @Operation(summary = "Listar espécies cadastradas, de forma paginada")
    @GetMapping
    ResponseEntity<Page<EspecieResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable
    );

    @Operation(summary = "Buscar uma espécie pelo ID")
    @GetMapping("/{id}")
    ResponseEntity<EspecieResponseDTO> buscarPorId(@Parameter(description = "ID da espécie") @PathVariable Long id);

    @Operation(summary = "Atualizar os dados de uma espécie existente")
    @PutMapping("/{id}")
    ResponseEntity<EspecieResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EspecieRequestDTO especieRequestDTO
    );

    @Operation(summary = "Excluir uma espécie (não permitido se houver raças ou animais vinculados)")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Long id);
}
