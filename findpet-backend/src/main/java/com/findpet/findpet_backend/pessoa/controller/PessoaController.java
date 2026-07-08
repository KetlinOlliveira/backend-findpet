package com.findpet.findpet_backend.pessoa.controller;

import com.findpet.findpet_backend.pessoa.dto.PessoaRequestDTO;
import com.findpet.findpet_backend.pessoa.dto.PessoaResponseDTO;
import com.findpet.findpet_backend.pessoa.model.Pessoa;
import com.findpet.findpet_backend.pessoa.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pessoas")
@CrossOrigin(origins = "*")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> cadastrar(
            @Valid @RequestBody PessoaRequestDTO pessoaRequestDTO
    ) {
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setCpf(pessoaRequestDTO.getCpf());
        novaPessoa.setTelefone(pessoaRequestDTO.getTelefone());
        novaPessoa.setDataNascimento(pessoaRequestDTO.getDataNascimento());

        Pessoa pessoa = pessoaService.cadastrar(
                novaPessoa,
                pessoaRequestDTO.getUsuarioId(),
                pessoaRequestDTO.getEnderecoId()
        );

        PessoaResponseDTO responseDTO = converterParaResponse(pessoa);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PessoaResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = "cpf", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        Page<PessoaResponseDTO> pessoas = pessoaService.listarTodos(pageable)
                .map(this::converterParaResponse);

        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> buscarPorId(@PathVariable Long id) {
        Pessoa pessoa = pessoaService.buscarPorId(id);

        PessoaResponseDTO responseDTO = converterParaResponse(pessoa);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PessoaRequestDTO pessoaRequestDTO
    ) {
        Pessoa pessoaAtualizada = new Pessoa();
        pessoaAtualizada.setCpf(pessoaRequestDTO.getCpf());
        pessoaAtualizada.setTelefone(pessoaRequestDTO.getTelefone());
        pessoaAtualizada.setDataNascimento(pessoaRequestDTO.getDataNascimento());

        Pessoa pessoa = pessoaService.atualizar(
                id,
                pessoaAtualizada,
                pessoaRequestDTO.getUsuarioId(),
                pessoaRequestDTO.getEnderecoId()
        );

        PessoaResponseDTO responseDTO = converterParaResponse(pessoa);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pessoaService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    private PessoaResponseDTO converterParaResponse(Pessoa pessoa) {
        PessoaResponseDTO responseDTO = new PessoaResponseDTO();

        responseDTO.setId(pessoa.getId());
        responseDTO.setCpf(pessoa.getCpf());
        responseDTO.setTelefone(pessoa.getTelefone());
        responseDTO.setDataNascimento(pessoa.getDataNascimento());

        if (pessoa.getUsuario() != null) {
            responseDTO.setUsuarioId(pessoa.getUsuario().getId());
            responseDTO.setUsuarioNome(pessoa.getUsuario().getNome());
            responseDTO.setUsuarioEmail(pessoa.getUsuario().getEmail());
        }

        if (pessoa.getEndereco() != null) {
            responseDTO.setEnderecoId(pessoa.getEndereco().getId());
            responseDTO.setCep(pessoa.getEndereco().getCep());
            responseDTO.setCidade(pessoa.getEndereco().getCidade());
            responseDTO.setEstado(pessoa.getEndereco().getEstado());
        }

        return responseDTO;
    }
}