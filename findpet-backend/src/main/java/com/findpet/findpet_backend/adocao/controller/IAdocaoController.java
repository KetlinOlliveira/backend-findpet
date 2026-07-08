package com.findpet.findpet_backend.adocao.controller;

import com.findpet.findpet_backend.adocao.dto.AdocaoRequestDTO;
import com.findpet.findpet_backend.adocao.dto.AdocaoResponseDTO;
import com.findpet.findpet_backend.adocao.dto.AdocaoStatusUpdateDTO;
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
 * Contrato REST do módulo de adoções.
 * As anotações de mapeamento HTTP e de documentação (Swagger) ficam aqui;
 * a implementação concreta só executa a lógica e monta as respostas.
 */
@Tag(name = "Adoções", description = "Fluxo de solicitação, análise e conclusão de adoções de animais.")
public interface IAdocaoController {

    @Operation(summary = "Solicitar a adoção de um animal disponível")
    @PostMapping
    ResponseEntity<AdocaoResponseDTO> solicitar(@Valid @RequestBody AdocaoRequestDTO adocaoRequestDTO);

    @Operation(summary = "Listar solicitações de adoção, de forma paginada")
    @GetMapping
    ResponseEntity<Page<AdocaoResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = "dataSolicitacao", direction = Sort.Direction.DESC) Pageable pageable
    );

    @Operation(summary = "Buscar uma solicitação de adoção pelo ID")
    @GetMapping("/{id}")
    ResponseEntity<AdocaoResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Atualizar o status de uma solicitação de adoção (em análise, aprovada, recusada ou cancelada)")
    @PatchMapping("/{id}/status")
    ResponseEntity<AdocaoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AdocaoStatusUpdateDTO statusUpdateDTO
    );

    @Operation(summary = "Excluir uma solicitação de adoção pendente")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Long id);
}
