package com.findpet.findpet_backend.especie.service;

import com.findpet.findpet_backend.animal.repository.AnimalRepository;
import com.findpet.findpet_backend.especie.model.Especie;
import com.findpet.findpet_backend.especie.repository.EspecieRepository;
import com.findpet.findpet_backend.infrastructure.exception.BusinessException;
import com.findpet.findpet_backend.raca.repository.RacaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EspecieService {

    private final EspecieRepository especieRepository;
    private final RacaRepository racaRepository;
    private final AnimalRepository animalRepository;

    public EspecieService(
            EspecieRepository especieRepository,
            RacaRepository racaRepository,
            AnimalRepository animalRepository
    ) {
        this.especieRepository = especieRepository;
        this.racaRepository = racaRepository;
        this.animalRepository = animalRepository;
    }

    @Transactional
    public Especie cadastrar(Especie especie) {
        String nomeNormalizado = normalizarNome(especie.getNome());

        if (especieRepository.existsByNomeIgnoreCase(nomeNormalizado)) {
            throw new BusinessException("Espécie já cadastrada.");
        }

        especie.setNome(nomeNormalizado);

        return especieRepository.save(especie);
    }

    public Page<Especie> listarTodos(Pageable pageable) {
        return especieRepository.findAll(pageable);
    }

    public Especie buscarPorId(Long id) {
        return especieRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Espécie não encontrada."));
    }

    @Transactional
    public Especie atualizar(Long id, Especie especieAtualizada) {
        Especie especie = buscarPorId(id);

        String nomeNormalizado = normalizarNome(especieAtualizada.getNome());

        especieRepository.findByNomeIgnoreCase(nomeNormalizado)
                .ifPresent(especieComMesmoNome -> {
                    if (!especieComMesmoNome.getId().equals(id)) {
                        throw new BusinessException("Este nome de espécie já está sendo usado.");
                    }
                });

        especie.setNome(nomeNormalizado);

        return especieRepository.save(especie);
    }

    @Transactional
    public void excluir(Long id) {
        Especie especie = buscarPorId(id);

        if (racaRepository.existsByEspecieId(id)) {
            throw new BusinessException("Não é possível excluir uma espécie que possui raças cadastradas.");
        }

        if (animalRepository.existsByEspecieId(id)) {
            throw new BusinessException("Não é possível excluir uma espécie que possui animais cadastrados.");
        }

        especieRepository.delete(especie);
    }

    private String normalizarNome(String nome) {
        if (nome == null) {
            return null;
        }

        String nomeTratado = nome.trim();

        if (nomeTratado.isEmpty()) {
            return nomeTratado;
        }

        return nomeTratado.substring(0, 1).toUpperCase() + nomeTratado.substring(1).toLowerCase();
    }
}
