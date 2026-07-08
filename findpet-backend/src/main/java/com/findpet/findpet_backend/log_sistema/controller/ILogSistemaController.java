package com.findpet.findpet_backend.log_sistema.controller;

import com.findpet.findpet_backend.log_sistema.dto.LogSistemaRequestDTO;
import com.findpet.findpet_backend.log_sistema.dto.LogSistemaResponseDTO;
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
 * Contrato REST do módulo de logs de sistema (trilha de auditoria).
 * Propositalmente não expõe atualização nem exclusão: uma vez registrado,
 * o log não pode ser alterado.
 */
@Tag(name = "Logs de sistema", description = "Trilha de auditoria das ações realizadas no sistema.")
public interface ILogSistemaController {

    @Operation(summary = "Registrar uma nova entrada de log")
    @PostMapping
    ResponseEntity<LogSistemaResponseDTO> registrar(@Valid @RequestBody LogSistemaRequestDTO logSistemaRequestDTO);

    @Operation(summary = "Listar logs registrados, de forma paginada e com filtro opcional por usuário")
    @GetMapping
    ResponseEntity<Page<LogSistemaResponseDTO>> listarTodos(
            @RequestParam(required = false) Long usuarioId,
            @PageableDefault(size = 10, sort = "dataHora", direction = Sort.Direction.DESC) Pageable pageable
    );

    @Operation(summary = "Buscar um log pelo ID")
    @GetMapping("/{id}")
    ResponseEntity<LogSistemaResponseDTO> buscarPorId(@PathVariable Long id);
}
