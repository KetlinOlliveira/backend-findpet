package com.findpet.findpet_backend.endereco.controller;

import com.findpet.findpet_backend.endereco.dto.EnderecoRequestDTO;
import com.findpet.findpet_backend.endereco.dto.EnderecoResponseDTO;
import com.findpet.findpet_backend.endereco.model.Endereco;
import com.findpet.findpet_backend.endereco.service.EnderecoService;
import com.findpet.findpet_backend.infrastructure.mapper.ObjectMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enderecos")
@CrossOrigin(origins = "*")
public class EnderecoController implements IEnderecoController {

    private final EnderecoService enderecoService;
    private final ObjectMapperUtil objectMapperUtil;

    public EnderecoController(
            EnderecoService enderecoService,
            ObjectMapperUtil objectMapperUtil
    ) {
        this.enderecoService = enderecoService;
        this.objectMapperUtil = objectMapperUtil;
    }

    @Override
    public ResponseEntity<EnderecoResponseDTO> cadastrar(EnderecoRequestDTO enderecoRequestDTO) {
        Endereco endereco = objectMapperUtil.map(enderecoRequestDTO, Endereco.class);

        Endereco enderecoSalvo = enderecoService.cadastrar(endereco);

        EnderecoResponseDTO responseDTO = objectMapperUtil.map(enderecoSalvo, EnderecoResponseDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Override
    public ResponseEntity<Page<EnderecoResponseDTO>> listarTodos(Pageable pageable) {
        Page<EnderecoResponseDTO> enderecos = enderecoService.listarTodos(pageable)
                .map(endereco -> objectMapperUtil.map(endereco, EnderecoResponseDTO.class));

        return ResponseEntity.ok(enderecos);
    }

    @Override
    public ResponseEntity<EnderecoResponseDTO> buscarPorId(Long id) {
        Endereco endereco = enderecoService.buscarPorId(id);

        EnderecoResponseDTO responseDTO = objectMapperUtil.map(endereco, EnderecoResponseDTO.class);

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<EnderecoResponseDTO> atualizar(Long id, EnderecoRequestDTO enderecoRequestDTO) {
        Endereco enderecoAtualizado = objectMapperUtil.map(enderecoRequestDTO, Endereco.class);

        Endereco endereco = enderecoService.atualizar(id, enderecoAtualizado);

        EnderecoResponseDTO responseDTO = objectMapperUtil.map(endereco, EnderecoResponseDTO.class);

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<Void> excluir(Long id) {
        enderecoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
