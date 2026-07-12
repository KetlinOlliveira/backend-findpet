package com.findpet.findpet_backend.usuario.controller;

import com.findpet.findpet_backend.usuario.dto.LoginRequestDTO;
import com.findpet.findpet_backend.usuario.dto.LoginResponseDTO;
import com.findpet.findpet_backend.usuario.dto.UsuarioRequestDTO;
import com.findpet.findpet_backend.usuario.dto.UsuarioResponseDTO;
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
 * Contrato REST do módulo de usuários.
 * As anotações de mapeamento HTTP e de documentação (Swagger) ficam aqui;
 * a implementação concreta só executa a lógica e monta as respostas.
 */
@Tag(name = "Usuários", description = "Cadastro, login e gerenciamento de usuários do sistema.")
public interface IUsuarioController {

    @Operation(summary = "Cadastrar um novo usuário")
    @PostMapping("/cadastro")
    ResponseEntity<UsuarioResponseDTO> cadastrar(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO);

    @Operation(summary = "Autenticar um usuário pelo email e senha e obter um token JWT")
    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO);

    @Operation(summary = "Listar usuários cadastrados, de forma paginada")
    @GetMapping
    ResponseEntity<Page<UsuarioResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable
    );

    @Operation(summary = "Buscar um usuário pelo ID")
    @GetMapping("/{id}")
    ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Atualizar os dados de um usuário existente")
    @PutMapping("/{id}")
    ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO
    );

    @Operation(summary = "Excluir um usuário")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Long id);
}
