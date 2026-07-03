package com.findpet.findpet_backend.endereco.controller;

import com.findpet.findpet_backend.endereco.dto.EnderecoRequestDTO;
import com.findpet.findpet_backend.endereco.dto.EnderecoResponseDTO;
import com.findpet.findpet_backend.endereco.model.Endereco;
import com.findpet.findpet_backend.endereco.service.EnderecoService;
import com.findpet.findpet_backend.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enderecos")
@CrossOrigin(origins = "*")
public class EnderecoController {

    private final EnderecoService enderecoService;
    private final ObjectMapperUtil objectMapperUtil;

    public EnderecoController(
            EnderecoService enderecoService,
            ObjectMapperUtil objectMapperUtil
    ) {
        this.enderecoService = enderecoService;
        this.objectMapperUtil = objectMapperUtil;
    }

    //Método http de POST para cadastrar endereço, recebendo um DTO de entrada e retornando um DTO de resposta.
    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> cadastrar(
            @Valid @RequestBody EnderecoRequestDTO enderecoRequestDTO
    ) {
        Endereco endereco = objectMapperUtil.map(enderecoRequestDTO, Endereco.class);

        Endereco enderecoSalvo = enderecoService.cadastrar(endereco);

        EnderecoResponseDTO responseDTO = objectMapperUtil.map(
                enderecoSalvo,
                EnderecoResponseDTO.class
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    //Medodo http de GET para listar todos os endereços, com paginação e ordenação.
    @GetMapping
    public ResponseEntity<Page<EnderecoResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = "cidade", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        Page<EnderecoResponseDTO> enderecos = enderecoService.listarTodos(pageable)
                .map(endereco -> objectMapperUtil.map(endereco, EnderecoResponseDTO.class));

        return ResponseEntity.ok(enderecos);
    }

    //Método http de GET para buscar um endereço por ID, retornando um DTO de resposta.
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> buscarPorId(@PathVariable Long id) {
        Endereco endereco = enderecoService.buscarPorId(id);

        EnderecoResponseDTO responseDTO = objectMapperUtil.map(
                endereco,
                EnderecoResponseDTO.class
        );

        return ResponseEntity.ok(responseDTO);
    }


    //Método http de PUT para atualizar um endereço, recebendo um DTO de entrada e retornando um DTO de resposta.

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EnderecoRequestDTO enderecoRequestDTO
    ) {
        Endereco enderecoAtualizado = objectMapperUtil.map(
                enderecoRequestDTO,
                Endereco.class
        );

        Endereco endereco = enderecoService.atualizar(id, enderecoAtualizado);

        EnderecoResponseDTO responseDTO = objectMapperUtil.map(
                endereco,
                EnderecoResponseDTO.class
        );

        return ResponseEntity.ok(responseDTO);
    }

    //Método http de DELETE para excluir um endereço por ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        enderecoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}