package com.findpet.findpet_backend.cep.controller;

import com.findpet.findpet_backend.cep.dto.CepResponseDTO;
import com.findpet.findpet_backend.cep.service.CepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * Controller criado para demonstrar o consumo de uma API externa com WebClient.
 */
@RestController
@RequestMapping("/api/ceps")
@CrossOrigin(origins = "*")
public class CepController {

    private final CepService cepService;

    public CepController(CepService cepService) {
        this.cepService = cepService;
    }

    /*
     * Endpoint para consultar um CEP usando a BrasilAPI.
     */
    @GetMapping("/{cep}")
    public ResponseEntity<CepResponseDTO> buscarCep(@PathVariable String cep) {
        CepResponseDTO responseDTO = cepService.buscarCep(cep);

        return ResponseEntity.ok(responseDTO);
    }
}