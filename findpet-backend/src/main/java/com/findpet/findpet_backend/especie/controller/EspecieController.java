package com.findpet.findpet_backend.especie.controller;

import com.findpet.findpet_backend.especie.dto.EspecieRequestDTO;
import com.findpet.findpet_backend.especie.dto.EspecieResponseDTO;
import com.findpet.findpet_backend.especie.model.Especie;
import com.findpet.findpet_backend.especie.service.EspecieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/especies")
@CrossOrigin(origins = "*")
public class EspecieController implements IEspecieController {

    private final EspecieService especieService;

    public EspecieController(EspecieService especieService) {
        this.especieService = especieService;
    }

    @Override
    public ResponseEntity<EspecieResponseDTO> cadastrar(EspecieRequestDTO especieRequestDTO) {
        Especie especie = new Especie();
        especie.setNome(especieRequestDTO.getNome());

        Especie especieSalva = especieService.cadastrar(especie);

        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaResponse(especieSalva));
    }

    @Override
    public ResponseEntity<Page<EspecieResponseDTO>> listarTodos(Pageable pageable) {
        Page<EspecieResponseDTO> especies = especieService.listarTodos(pageable)
                .map(this::converterParaResponse);

        return ResponseEntity.ok(especies);
    }

    @Override
    public ResponseEntity<EspecieResponseDTO> buscarPorId(Long id) {
        Especie especie = especieService.buscarPorId(id);

        return ResponseEntity.ok(converterParaResponse(especie));
    }

    @Override
    public ResponseEntity<EspecieResponseDTO> atualizar(Long id, EspecieRequestDTO especieRequestDTO) {
        Especie especieAtualizada = new Especie();
        especieAtualizada.setNome(especieRequestDTO.getNome());

        Especie especie = especieService.atualizar(id, especieAtualizada);

        return ResponseEntity.ok(converterParaResponse(especie));
    }

    @Override
    public ResponseEntity<Void> excluir(Long id) {
        especieService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    private EspecieResponseDTO converterParaResponse(Especie especie) {
        EspecieResponseDTO responseDTO = new EspecieResponseDTO();

        responseDTO.setId(especie.getId());
        responseDTO.setNome(especie.getNome());

        return responseDTO;
    }
}
