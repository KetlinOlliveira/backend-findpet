package com.findpet.findpet_backend.perfil.controller;

import com.findpet.findpet_backend.infrastructure.mapper.ObjectMapperUtil;
import com.findpet.findpet_backend.perfil.dto.request.PerfilRequestDTO;
import com.findpet.findpet_backend.perfil.dto.response.PerfilResponseDTO;
import com.findpet.findpet_backend.perfil.model.Perfil;
import com.findpet.findpet_backend.perfil.service.PerfilService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfis")
@CrossOrigin(origins = "*")
public class PerfilController {

    private final PerfilService perfilService;
    private final ObjectMapperUtil objectMapperUtil;

    public PerfilController(
            PerfilService perfilService,
            ObjectMapperUtil objectMapperUtil
    ) {
        this.perfilService = perfilService;
        this.objectMapperUtil = objectMapperUtil;
    }

    @PostMapping
    public ResponseEntity<PerfilResponseDTO> cadastrar(
            @Valid @RequestBody PerfilRequestDTO perfilRequestDTO
    ) {
        Perfil perfil = objectMapperUtil.map(perfilRequestDTO, Perfil.class);

        Perfil perfilSalvo = perfilService.cadastrar(perfil);

        PerfilResponseDTO responseDTO = objectMapperUtil.map(
                perfilSalvo,
                PerfilResponseDTO.class
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PerfilResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        Page<PerfilResponseDTO> perfis = perfilService.listarTodos(pageable)
                .map(perfil -> objectMapperUtil.map(perfil, PerfilResponseDTO.class));

        return ResponseEntity.ok(perfis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> buscarPorId(@PathVariable Long id) {
        Perfil perfil = perfilService.buscarPorId(id);

        PerfilResponseDTO responseDTO = objectMapperUtil.map(
                perfil,
                PerfilResponseDTO.class
        );

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PerfilRequestDTO perfilRequestDTO
    ) {
        Perfil perfilAtualizado = objectMapperUtil.map(
                perfilRequestDTO,
                Perfil.class
        );

        Perfil perfil = perfilService.atualizar(id, perfilAtualizado);

        PerfilResponseDTO responseDTO = objectMapperUtil.map(
                perfil,
                PerfilResponseDTO.class
        );

        return ResponseEntity.ok(responseDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        perfilService.excluir(id);

        return ResponseEntity.noContent().build();
    }
} 
