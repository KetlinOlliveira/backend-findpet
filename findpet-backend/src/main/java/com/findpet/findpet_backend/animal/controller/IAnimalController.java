package com.findpet.findpet_backend.animal.controller;

import com.findpet.findpet_backend.animal.dto.AnimalRequestDTO;
import com.findpet.findpet_backend.animal.dto.AnimalResponseDTO;
import com.findpet.findpet_backend.animal.enums.StatusAnimal;
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
 * Contrato REST do módulo de animais.
 * As anotações de mapeamento HTTP e de documentação (Swagger) ficam aqui;
 * a implementação concreta só executa a lógica e monta as respostas.
 */
@Tag(name = "Animais", description = "Cadastro e consulta dos animais disponíveis para adoção.")
public interface IAnimalController {

    @Operation(summary = "Cadastrar um novo animal para adoção")
    @PostMapping
    ResponseEntity<AnimalResponseDTO> cadastrar(@Valid @RequestBody AnimalRequestDTO animalRequestDTO);

    @Operation(summary = "Listar animais cadastrados, de forma paginada e com filtro opcional por status")
    @GetMapping
    ResponseEntity<Page<AnimalResponseDTO>> listarTodos(
            @RequestParam(required = false) StatusAnimal status,
            @PageableDefault(size = 10, sort = "dataCadastro", direction = Sort.Direction.DESC) Pageable pageable
    );

    @Operation(summary = "Buscar um animal pelo ID")
    @GetMapping("/{id}")
    ResponseEntity<AnimalResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Atualizar os dados de um animal existente")
    @PutMapping("/{id}")
    ResponseEntity<AnimalResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AnimalRequestDTO animalRequestDTO
    );

    @Operation(summary = "Excluir um animal (não permitido se houver adoção em andamento ou concluída)")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Long id);
}
