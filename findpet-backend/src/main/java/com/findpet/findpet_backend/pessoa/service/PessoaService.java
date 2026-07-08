package com.findpet.findpet_backend.pessoa.service;

import com.findpet.findpet_backend.endereco.model.Endereco;
import com.findpet.findpet_backend.endereco.repository.EnderecoRepository;
import com.findpet.findpet_backend.infrastructure.exception.BusinessException;
import com.findpet.findpet_backend.pessoa.model.Pessoa;
import com.findpet.findpet_backend.pessoa.repository.PessoaRepository;
import com.findpet.findpet_backend.usuario.model.Usuario;
import com.findpet.findpet_backend.usuario.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;

    public PessoaService(
            PessoaRepository pessoaRepository,
            UsuarioRepository usuarioRepository,
            EnderecoRepository enderecoRepository
    ) {
        this.pessoaRepository = pessoaRepository;
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
    }

    @Transactional
    public Pessoa cadastrar(Pessoa pessoa, Long usuarioId, Long enderecoId) {
        if (pessoaRepository.existsByCpf(pessoa.getCpf())) {
            throw new BusinessException("CPF já cadastrado.");
        }

        pessoaRepository.findByUsuarioId(usuarioId)
                .ifPresent(pessoaExistente -> {
                    throw new BusinessException("Este usuário já possui uma pessoa cadastrada.");
                });

        pessoaRepository.findByEnderecoId(enderecoId)
                .ifPresent(pessoaExistente -> {
                    throw new BusinessException("Este endereço já está vinculado a uma pessoa.");
                });

        Usuario usuario = buscarUsuarioPorId(usuarioId);
        Endereco endereco = buscarEnderecoPorId(enderecoId);

        pessoa.setUsuario(usuario);
        pessoa.setEndereco(endereco);

        return pessoaRepository.save(pessoa);
    }

    public Page<Pessoa> listarTodos(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Pessoa não encontrada."));
    }

    @Transactional
    public Pessoa atualizar(Long id, Pessoa pessoaAtualizada, Long usuarioId, Long enderecoId) {
        Pessoa pessoa = buscarPorId(id);

        pessoaRepository.findByCpf(pessoaAtualizada.getCpf())
                .ifPresent(pessoaComMesmoCpf -> {
                    if (!pessoaComMesmoCpf.getId().equals(id)) {
                        throw new BusinessException("Este CPF já está sendo usado por outra pessoa.");
                    }
                });

        pessoaRepository.findByUsuarioId(usuarioId)
                .ifPresent(pessoaComMesmoUsuario -> {
                    if (!pessoaComMesmoUsuario.getId().equals(id)) {
                        throw new BusinessException("Este usuário já possui outra pessoa cadastrada.");
                    }
                });

        pessoaRepository.findByEnderecoId(enderecoId)
                .ifPresent(pessoaComMesmoEndereco -> {
                    if (!pessoaComMesmoEndereco.getId().equals(id)) {
                        throw new BusinessException("Este endereço já está vinculado a outra pessoa.");
                    }
                });

        Usuario usuario = buscarUsuarioPorId(usuarioId);
        Endereco endereco = buscarEnderecoPorId(enderecoId);

        pessoa.setCpf(pessoaAtualizada.getCpf());
        pessoa.setTelefone(pessoaAtualizada.getTelefone());
        pessoa.setDataNascimento(pessoaAtualizada.getDataNascimento());
        pessoa.setUsuario(usuario);
        pessoa.setEndereco(endereco);

        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public void excluir(Long id) {
        Pessoa pessoa = buscarPorId(id);

        pessoaRepository.delete(pessoa);
    }

    private Usuario buscarUsuarioPorId(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));
    }

    private Endereco buscarEnderecoPorId(Long enderecoId) {
        return enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new BusinessException("Endereço não encontrado."));
    }
}