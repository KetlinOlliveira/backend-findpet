package com.findpet.findpet_backend.cep.controller;

import com.findpet.findpet_backend.cep.dto.CepResponseDTO;
import com.findpet.findpet_backend.cep.service.CepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Controller criado para demonstrar o consumo de uma API externa com WebClient.
 */
@RestController
@RequestMapping("/api/ceps")
@CrossOrigin(origins = "*")
public class CepController implements ICepController {

    private final CepService cepService;

    public CepController(CepService cepService) {
        this.cepService = cepService;
    }

    @Override
    public ResponseEntity<CepResponseDTO> buscarCep(String cep) {
        CepResponseDTO responseDTO = cepService.buscarCep(cep);

        return ResponseEntity.ok(responseDTO);
    }
}
