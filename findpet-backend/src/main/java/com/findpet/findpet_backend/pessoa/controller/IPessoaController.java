package com.findpet.findpet_backend.pessoa.controller;

import com.findpet.findpet_backend.pessoa.dto.PessoaRequestDTO;
import com.findpet.findpet_backend.pessoa.dto.PessoaResponseDTO;
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
 * Contrato REST do módulo de pessoas.
 * As anotações de mapeamento HTTP e de documentação (Swagger) ficam aqui;
 * a implementação concreta só executa a lógica e monta as respostas.
 */
@Tag(name = "Pessoas", description = "Dados pessoais (CPF, telefone, nascimento) vinculados a um usuário e um endereço.")
public interface IPessoaController {

    @Operation(summary = "Cadastrar os dados pessoais de um usuário")
    @PostMapping
    ResponseEntity<PessoaResponseDTO> cadastrar(@Valid @RequestBody PessoaRequestDTO pessoaRequestDTO);

    @Operation(summary = "Listar pessoas cadastradas, de forma paginada")
    @GetMapping
    ResponseEntity<Page<PessoaResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = "cpf", direction = Sort.Direction.ASC) Pageable pageable
    );

    @Operation(summary = "Buscar uma pessoa pelo ID")
    @GetMapping("/{id}")
    ResponseEntity<PessoaResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Atualizar os dados de uma pessoa existente")
    @PutMapping("/{id}")
    ResponseEntity<PessoaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PessoaRequestDTO pessoaRequestDTO
    );

    @Operation(summary = "Excluir uma pessoa")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Long id);
}
