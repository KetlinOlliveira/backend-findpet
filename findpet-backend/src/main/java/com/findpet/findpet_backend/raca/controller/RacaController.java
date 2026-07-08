package com.findpet.findpet_backend.raca.controller;

import com.findpet.findpet_backend.raca.dto.RacaRequestDTO;
import com.findpet.findpet_backend.raca.dto.RacaResponseDTO;
import com.findpet.findpet_backend.raca.model.Raca;
import com.findpet.findpet_backend.raca.service.RacaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/racas")
@CrossOrigin(origins = "*")
public class RacaController implements IRacaController {

    private final RacaService racaService;

    public RacaController(RacaService racaService) {
        this.racaService = racaService;
    }

    @Override
    public ResponseEntity<RacaResponseDTO> cadastrar(RacaRequestDTO racaRequestDTO) {
        Raca raca = new Raca();
        raca.setNome(racaRequestDTO.getNome());

        Raca racaSalva = racaService.cadastrar(raca, racaRequestDTO.getEspecieId());

        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaResponse(racaSalva));
    }

    @Override
    public ResponseEntity<Page<RacaResponseDTO>> listarTodos(Pageable pageable) {
        Page<RacaResponseDTO> racas = racaService.listarTodos(pageable)
                .map(this::converterParaResponse);

        return ResponseEntity.ok(racas);
    }

    @Override
    public ResponseEntity<RacaResponseDTO> buscarPorId(Long id) {
        Raca raca = racaService.buscarPorId(id);

        return ResponseEntity.ok(converterParaResponse(raca));
    }

    @Override
    public ResponseEntity<RacaResponseDTO> atualizar(Long id, RacaRequestDTO racaRequestDTO) {
        Raca racaAtualizada = new Raca();
        racaAtualizada.setNome(racaRequestDTO.getNome());

        Raca raca = racaService.atualizar(id, racaAtualizada, racaRequestDTO.getEspecieId());

        return ResponseEntity.ok(converterParaResponse(raca));
    }

    @Override
    public ResponseEntity<Void> excluir(Long id) {
        racaService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    private RacaResponseDTO converterParaResponse(Raca raca) {
        RacaResponseDTO responseDTO = new RacaResponseDTO();

        responseDTO.setId(raca.getId());
        responseDTO.setNome(raca.getNome());

        if (raca.getEspecie() != null) {
            responseDTO.setEspecieId(raca.getEspecie().getId());
            responseDTO.setEspecieNome(raca.getEspecie().getNome());
        }

        return responseDTO;
    }
}
