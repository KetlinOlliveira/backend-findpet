package com.findpet.findpet_backend.endereco.service;

import com.findpet.findpet_backend.endereco.model.Endereco;
import com.findpet.findpet_backend.endereco.repository.EnderecoRepository;
import com.findpet.findpet_backend.infrastructure.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Transactional
    public Endereco cadastrar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public Page<Endereco> listarTodos(Pageable pageable) {
        return enderecoRepository.findAll(pageable);
    }

    public Endereco buscarPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Endereço não encontrado."));
    }

    @Transactional
    public Endereco atualizar(Long id, Endereco enderecoAtualizado) {
        Endereco endereco = buscarPorId(id);

        endereco.setCep(enderecoAtualizado.getCep());
        endereco.setEstado(enderecoAtualizado.getEstado());
        endereco.setCidade(enderecoAtualizado.getCidade());
        endereco.setBairro(enderecoAtualizado.getBairro());
        endereco.setRua(enderecoAtualizado.getRua());
        endereco.setNumero(enderecoAtualizado.getNumero());
        endereco.setComplemento(enderecoAtualizado.getComplemento());

        return enderecoRepository.save(endereco);
    }

    @Transactional
    public void excluir(Long id) {
        Endereco endereco = buscarPorId(id);

        enderecoRepository.delete(endereco);
    }
}