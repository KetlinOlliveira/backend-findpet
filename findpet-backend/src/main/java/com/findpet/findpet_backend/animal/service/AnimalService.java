package com.findpet.findpet_backend.animal.service;

import com.findpet.findpet_backend.adocao.anums.StatusAdocao;
import com.findpet.findpet_backend.adocao.respository.AdocaoRepository;
import com.findpet.findpet_backend.animal.enums.StatusAnimal;
import com.findpet.findpet_backend.animal.model.Animal;
import com.findpet.findpet_backend.animal.repository.AnimalRepository;
import com.findpet.findpet_backend.especie.model.Especie;
import com.findpet.findpet_backend.especie.repository.EspecieRepository;
import com.findpet.findpet_backend.infrastructure.exception.BusinessException;
import com.findpet.findpet_backend.log_sistema.enums.AcaoLogSistema;
import com.findpet.findpet_backend.log_sistema.model.LogSistema;
import com.findpet.findpet_backend.log_sistema.repository.LogSistemaRepository;
import com.findpet.findpet_backend.raca.model.Raca;
import com.findpet.findpet_backend.raca.repository.RacaRepository;
import com.findpet.findpet_backend.usuario.model.Usuario;
import com.findpet.findpet_backend.usuario.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AnimalService {

    private static final List<StatusAdocao> STATUS_ADOCAO_IMPEDITIVOS = List.of(
            StatusAdocao.SOLICITADA, StatusAdocao.EM_ANALISE, StatusAdocao.APROVADA
    );

    private final AnimalRepository animalRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecieRepository especieRepository;
    private final RacaRepository racaRepository;
    private final AdocaoRepository adocaoRepository;
    private final LogSistemaRepository logSistemaRepository;

    public AnimalService(
            AnimalRepository animalRepository,
            UsuarioRepository usuarioRepository,
            EspecieRepository especieRepository,
            RacaRepository racaRepository,
            AdocaoRepository adocaoRepository,
            LogSistemaRepository logSistemaRepository
    ) {
        this.animalRepository = animalRepository;
        this.usuarioRepository = usuarioRepository;
        this.especieRepository = especieRepository;
        this.racaRepository = racaRepository;
        this.adocaoRepository = adocaoRepository;
        this.logSistemaRepository = logSistemaRepository;
    }

    @Transactional
    public Animal cadastrar(Animal animal, Long usuarioId, Long especieId, Long racaId) {
        Usuario usuario = buscarUsuarioPorId(usuarioId);
        Especie especie = buscarEspeciePorId(especieId);
        Raca raca = buscarRacaValidandoEspecie(racaId, especieId);

        animal.setUsuario(usuario);
        animal.setEspecie(especie);
        animal.setRaca(raca);
        animal.setStatus(StatusAnimal.DISPONIVEL);

        Animal animalSalvo = animalRepository.save(animal);

        registrarLog(usuario, AcaoLogSistema.CADASTRO_ANIMAL, "Animal '" + animalSalvo.getNome() + "' cadastrado.");

        return animalSalvo;
    }

    public Page<Animal> listarTodos(Pageable pageable) {
        return animalRepository.findAll(pageable);
    }

    public Page<Animal> listarPorStatus(StatusAnimal status, Pageable pageable) {
        return animalRepository.findByStatus(status, pageable);
    }

    public Animal buscarPorId(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Animal não encontrado."));
    }

    @Transactional
    public Animal atualizar(Long id, Animal animalAtualizado, Long usuarioId, Long especieId, Long racaId) {
        Animal animal = buscarPorId(id);
        Usuario usuario = buscarUsuarioPorId(usuarioId);
        Especie especie = buscarEspeciePorId(especieId);
        Raca raca = buscarRacaValidandoEspecie(racaId, especieId);

        animal.setNome(animalAtualizado.getNome());
        animal.setIdade(animalAtualizado.getIdade());
        animal.setDescricao(animalAtualizado.getDescricao());
        animal.setFotoUrl(animalAtualizado.getFotoUrl());
        animal.setUsuario(usuario);
        animal.setEspecie(especie);
        animal.setRaca(raca);

        Animal animalSalvo = animalRepository.save(animal);

        registrarLog(usuario, AcaoLogSistema.ATUALIZACAO_ANIMAL, "Animal '" + animalSalvo.getNome() + "' atualizado.");

        return animalSalvo;
    }

    @Transactional
    public void excluir(Long id) {
        Animal animal = buscarPorId(id);

        if (adocaoRepository.existsByAnimalIdAndStatusIn(id, STATUS_ADOCAO_IMPEDITIVOS)) {
            throw new BusinessException("Não é possível excluir um animal com solicitação de adoção em andamento ou concluída.");
        }

        animalRepository.delete(animal);

        registrarLog(animal.getUsuario(), AcaoLogSistema.EXCLUSAO_ANIMAL, "Animal '" + animal.getNome() + "' excluído.");
    }

    private Raca buscarRacaValidandoEspecie(Long racaId, Long especieId) {
        Raca raca = racaRepository.findById(racaId)
                .orElseThrow(() -> new BusinessException("Raça não encontrada."));

        if (!raca.getEspecie().getId().equals(especieId)) {
            throw new BusinessException("A raça informada não pertence à espécie selecionada.");
        }

        return raca;
    }

    private Usuario buscarUsuarioPorId(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));
    }

    private Especie buscarEspeciePorId(Long especieId) {
        return especieRepository.findById(especieId)
                .orElseThrow(() -> new BusinessException("Espécie não encontrada."));
    }

    private void registrarLog(Usuario usuario, AcaoLogSistema acao, String descricao) {
        LogSistema log = new LogSistema();
        log.setUsuario(usuario);
        log.setAcao(acao);
        log.setDescricao(descricao);

        logSistemaRepository.save(log);
    }
}
