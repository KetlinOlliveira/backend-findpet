package com.findpet.findpet_backend.pessoa.controller;

import com.findpet.findpet_backend.pessoa.dto.PessoaRequestDTO;
import com.findpet.findpet_backend.pessoa.dto.PessoaResponseDTO;
import com.findpet.findpet_backend.pessoa.model.Pessoa;
import com.findpet.findpet_backend.pessoa.service.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pessoas")
@CrossOrigin(origins = "*")
public class PessoaController implements IPessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @Override
    public ResponseEntity<PessoaResponseDTO> cadastrar(PessoaRequestDTO pessoaRequestDTO) {
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setCpf(pessoaRequestDTO.getCpf());
        novaPessoa.setTelefone(pessoaRequestDTO.getTelefone());
        novaPessoa.setDataNascimento(pessoaRequestDTO.getDataNascimento());

        Pessoa pessoa = pessoaService.cadastrar(
                novaPessoa,
                pessoaRequestDTO.getUsuarioId(),
                pessoaRequestDTO.getEnderecoId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaResponse(pessoa));
    }

    @Override
    public ResponseEntity<Page<PessoaResponseDTO>> listarTodos(Pageable pageable) {
        Page<PessoaResponseDTO> pessoas = pessoaService.listarTodos(pageable)
                .map(this::converterParaResponse);

        return ResponseEntity.ok(pessoas);
    }

    @Override
    public ResponseEntity<PessoaResponseDTO> buscarPorId(Long id) {
        Pessoa pessoa = pessoaService.buscarPorId(id);

        return ResponseEntity.ok(converterParaResponse(pessoa));
    }

    @Override
    public ResponseEntity<PessoaResponseDTO> atualizar(Long id, PessoaRequestDTO pessoaRequestDTO) {
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

        return ResponseEntity.ok(converterParaResponse(pessoa));
    }

    @Override
    public ResponseEntity<Void> excluir(Long id) {
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
