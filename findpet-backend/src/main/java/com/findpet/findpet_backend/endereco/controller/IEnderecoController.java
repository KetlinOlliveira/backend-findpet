package com.findpet.findpet_backend.endereco.controller;

import com.findpet.findpet_backend.endereco.dto.EnderecoRequestDTO;
import com.findpet.findpet_backend.endereco.dto.EnderecoResponseDTO;
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
 * Contrato REST do módulo de endereços.
 * As anotações de mapeamento HTTP e de documentação (Swagger) ficam aqui;
 * a implementação concreta só executa a lógica e monta as respostas.
 */
@Tag(name = "Endereços", description = "Cadastro e consulta de endereços vinculados a uma pessoa.")
public interface IEnderecoController {

    @Operation(summary = "Cadastrar um novo endereço")
    @PostMapping
    ResponseEntity<EnderecoResponseDTO> cadastrar(@Valid @RequestBody EnderecoRequestDTO enderecoRequestDTO);

    @Operation(summary = "Listar endereços cadastrados, de forma paginada")
    @GetMapping
    ResponseEntity<Page<EnderecoResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = "cidade", direction = Sort.Direction.ASC) Pageable pageable
    );

    @Operation(summary = "Buscar um endereço pelo ID")
    @GetMapping("/{id}")
    ResponseEntity<EnderecoResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Atualizar os dados de um endereço existente")
    @PutMapping("/{id}")
    ResponseEntity<EnderecoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EnderecoRequestDTO enderecoRequestDTO
    );

    @Operation(summary = "Excluir um endereço")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Long id);
}
