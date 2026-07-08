package com.findpet.findpet_backend.perfil.controller;

import com.findpet.findpet_backend.perfil.dto.PerfilRequestDTO;
import com.findpet.findpet_backend.perfil.dto.PerfilResponseDTO;
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
 * Contrato REST do módulo de perfis.
 * As anotações de mapeamento HTTP e de documentação (Swagger) ficam aqui;
 * a implementação concreta só executa a lógica e monta as respostas.
 */
@Tag(name = "Perfis", description = "Perfis de acesso atribuídos aos usuários do sistema.")
public interface IPerfilController {

    @Operation(summary = "Cadastrar um novo perfil")
    @PostMapping
    ResponseEntity<PerfilResponseDTO> cadastrar(@Valid @RequestBody PerfilRequestDTO perfilRequestDTO);

    @Operation(summary = "Listar perfis cadastrados, de forma paginada")
    @GetMapping
    ResponseEntity<Page<PerfilResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable
    );

    @Operation(summary = "Buscar um perfil pelo ID")
    @GetMapping("/{id}")
    ResponseEntity<PerfilResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Atualizar os dados de um perfil existente")
    @PutMapping("/{id}")
    ResponseEntity<PerfilResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PerfilRequestDTO perfilRequestDTO
    );

    @Operation(summary = "Excluir um perfil")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Long id);
}
