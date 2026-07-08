package com.findpet.findpet_backend.adocao.controller;

import com.findpet.findpet_backend.adocao.dto.AdocaoRequestDTO;
import com.findpet.findpet_backend.adocao.dto.AdocaoResponseDTO;
import com.findpet.findpet_backend.adocao.dto.AdocaoStatusUpdateDTO;
import com.findpet.findpet_backend.adocao.model.Adocao;
import com.findpet.findpet_backend.adocao.service.AdocaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/adocoes")
@CrossOrigin(origins = "*")
public class AdocaoController implements IAdocaoController {

    private final AdocaoService adocaoService;

    public AdocaoController(AdocaoService adocaoService) {
        this.adocaoService = adocaoService;
    }

    @Override
    public ResponseEntity<AdocaoResponseDTO> solicitar(AdocaoRequestDTO adocaoRequestDTO) {
        Adocao adocao = adocaoService.solicitar(
                adocaoRequestDTO.getUsuarioId(),
                adocaoRequestDTO.getAnimalId(),
                adocaoRequestDTO.getObservacao()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaResponse(adocao));
    }

    @Override
    public ResponseEntity<Page<AdocaoResponseDTO>> listarTodos(Pageable pageable) {
        Page<AdocaoResponseDTO> adocoes = adocaoService.listarTodos(pageable)
                .map(this::converterParaResponse);

        return ResponseEntity.ok(adocoes);
    }

    @Override
    public ResponseEntity<AdocaoResponseDTO> buscarPorId(Long id) {
        Adocao adocao = adocaoService.buscarPorId(id);

        return ResponseEntity.ok(converterParaResponse(adocao));
    }

    @Override
    public ResponseEntity<AdocaoResponseDTO> atualizarStatus(Long id, AdocaoStatusUpdateDTO statusUpdateDTO) {
        Adocao adocao = adocaoService.atualizarStatus(id, statusUpdateDTO.getStatus());

        return ResponseEntity.ok(converterParaResponse(adocao));
    }

    @Override
    public ResponseEntity<Void> excluir(Long id) {
        adocaoService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    private AdocaoResponseDTO converterParaResponse(Adocao adocao) {
        AdocaoResponseDTO responseDTO = new AdocaoResponseDTO();

        responseDTO.setId(adocao.getId());
        responseDTO.setDataSolicitacao(adocao.getDataSolicitacao());
        responseDTO.setDataConclusao(adocao.getDataConclusao());
        responseDTO.setStatus(adocao.getStatus());
        responseDTO.setObservacao(adocao.getObservacao());

        if (adocao.getUsuario() != null) {
            responseDTO.setUsuarioId(adocao.getUsuario().getId());
            responseDTO.setUsuarioNome(adocao.getUsuario().getNome());
        }

        if (adocao.getAnimal() != null) {
            responseDTO.setAnimalId(adocao.getAnimal().getId());
            responseDTO.setAnimalNome(adocao.getAnimal().getNome());
        }

        return responseDTO;
    }
}
