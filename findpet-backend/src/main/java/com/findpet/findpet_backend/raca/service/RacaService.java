package com.findpet.findpet_backend.raca.service;

import com.findpet.findpet_backend.animal.repository.AnimalRepository;
import com.findpet.findpet_backend.especie.model.Especie;
import com.findpet.findpet_backend.especie.repository.EspecieRepository;
import com.findpet.findpet_backend.infrastructure.exception.BusinessException;
import com.findpet.findpet_backend.raca.model.Raca;
import com.findpet.findpet_backend.raca.repository.RacaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RacaService {

    private final RacaRepository racaRepository;
    private final EspecieRepository especieRepository;
    private final AnimalRepository animalRepository;

    public RacaService(
            RacaRepository racaRepository,
            EspecieRepository especieRepository,
            AnimalRepository animalRepository
    ) {
        this.racaRepository = racaRepository;
        this.especieRepository = especieRepository;
        this.animalRepository = animalRepository;
    }

    @Transactional
    public Raca cadastrar(Raca raca, Long especieId) {
        Especie especie = buscarEspeciePorId(especieId);

        String nomeNormalizado = normalizarNome(raca.getNome());

        if (racaRepository.existsByNomeIgnoreCaseAndEspecieId(nomeNormalizado, especieId)) {
            throw new BusinessException("Já existe uma raça com este nome para a espécie informada.");
        }

        raca.setNome(nomeNormalizado);
        raca.setEspecie(especie);

        return racaRepository.save(raca);
    }

    public Page<Raca> listarTodos(Pageable pageable) {
        return racaRepository.findAll(pageable);
    }

    public Raca buscarPorId(Long id) {
        return racaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Raça não encontrada."));
    }

    @Transactional
    public Raca atualizar(Long id, Raca racaAtualizada, Long especieId) {
        Raca raca = buscarPorId(id);
        Especie especie = buscarEspeciePorId(especieId);

        String nomeNormalizado = normalizarNome(racaAtualizada.getNome());

        racaRepository.findByNomeIgnoreCaseAndEspecieId(nomeNormalizado, especieId)
                .ifPresent(racaComMesmoNome -> {
                    if (!racaComMesmoNome.getId().equals(id)) {
                        throw new BusinessException("Já existe uma raça com este nome para a espécie informada.");
                    }
                });

        raca.setNome(nomeNormalizado);
        raca.setEspecie(especie);

        return racaRepository.save(raca);
    }

    @Transactional
    public void excluir(Long id) {
        Raca raca = buscarPorId(id);

        if (animalRepository.existsByRacaId(id)) {
            throw new BusinessException("Não é possível excluir uma raça que possui animais cadastrados.");
        }

        racaRepository.delete(raca);
    }

    private Especie buscarEspeciePorId(Long especieId) {
        return especieRepository.findById(especieId)
                .orElseThrow(() -> new BusinessException("Espécie não encontrada."));
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
