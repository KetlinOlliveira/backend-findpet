package com.findpet.findpet_backend.perfil.service;

import com.findpet.findpet_backend.infrastructure.exception.BusinessException;
import com.findpet.findpet_backend.perfil.model.Perfil;
import com.findpet.findpet_backend.perfil.repository.PerfilRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    @Transactional
    public Perfil cadastrar(Perfil perfil) {
        String nomeNormalizado = normalizarNome(perfil.getNome());

        if (perfilRepository.existsByNomeIgnoreCase(nomeNormalizado)) {
            throw new BusinessException("Perfil já cadastrado.");
        }

        perfil.setNome(nomeNormalizado);

        return perfilRepository.save(perfil);
    }

    public Page<Perfil> listarTodos(Pageable pageable) {
        return perfilRepository.findAll(pageable);
    }

    public Perfil buscarPorId(Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Perfil não encontrado."));
    }

    @Transactional
    public Perfil atualizar(Long id, Perfil perfilAtualizado) {
        Perfil perfil = buscarPorId(id);

        String nomeNormalizado = normalizarNome(perfilAtualizado.getNome());

        perfilRepository.findByNomeIgnoreCase(nomeNormalizado)
                .ifPresent(perfilComMesmoNome -> {
                    if (!perfilComMesmoNome.getId().equals(id)) {
                        throw new BusinessException("Este nome de perfil já está sendo usado.");
                    }
                });

        perfil.setNome(nomeNormalizado);
        perfil.setDescricao(perfilAtualizado.getDescricao());

        return perfilRepository.save(perfil);
    }

    @Transactional
    public void excluir(Long id) {
        Perfil perfil = buscarPorId(id);

        perfilRepository.delete(perfil);
    }

    private String normalizarNome(String nome) {
        if (nome == null) {
            return null;
        }

        return nome.trim().toUpperCase();
    }
} 
