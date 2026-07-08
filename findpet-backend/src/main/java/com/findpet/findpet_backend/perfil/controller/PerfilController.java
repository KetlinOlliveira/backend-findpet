package com.findpet.findpet_backend.perfil.controller;

import com.findpet.findpet_backend.infrastructure.mapper.ObjectMapperUtil;
import com.findpet.findpet_backend.perfil.dto.PerfilRequestDTO;
import com.findpet.findpet_backend.perfil.dto.PerfilResponseDTO;
import com.findpet.findpet_backend.perfil.model.Perfil;
import com.findpet.findpet_backend.perfil.service.PerfilService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/perfis")
@CrossOrigin(origins = "*")
public class PerfilController implements IPerfilController {

    private final PerfilService perfilService;
    private final ObjectMapperUtil objectMapperUtil;

    public PerfilController(
            PerfilService perfilService,
            ObjectMapperUtil objectMapperUtil
    ) {
        this.perfilService = perfilService;
        this.objectMapperUtil = objectMapperUtil;
    }

    @Override
    public ResponseEntity<PerfilResponseDTO> cadastrar(PerfilRequestDTO perfilRequestDTO) {
        Perfil perfil = objectMapperUtil.map(perfilRequestDTO, Perfil.class);

        Perfil perfilSalvo = perfilService.cadastrar(perfil);

        PerfilResponseDTO responseDTO = objectMapperUtil.map(perfilSalvo, PerfilResponseDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Override
    public ResponseEntity<Page<PerfilResponseDTO>> listarTodos(Pageable pageable) {
        Page<PerfilResponseDTO> perfis = perfilService.listarTodos(pageable)
                .map(perfil -> objectMapperUtil.map(perfil, PerfilResponseDTO.class));

        return ResponseEntity.ok(perfis);
    }

    @Override
    public ResponseEntity<PerfilResponseDTO> buscarPorId(Long id) {
        Perfil perfil = perfilService.buscarPorId(id);

        PerfilResponseDTO responseDTO = objectMapperUtil.map(perfil, PerfilResponseDTO.class);

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<PerfilResponseDTO> atualizar(Long id, PerfilRequestDTO perfilRequestDTO) {
        Perfil perfilAtualizado = objectMapperUtil.map(perfilRequestDTO, Perfil.class);

        Perfil perfil = perfilService.atualizar(id, perfilAtualizado);

        PerfilResponseDTO responseDTO = objectMapperUtil.map(perfil, PerfilResponseDTO.class);

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<Void> excluir(Long id) {
        perfilService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
