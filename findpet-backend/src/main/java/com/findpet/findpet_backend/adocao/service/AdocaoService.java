package com.findpet.findpet_backend.adocao.service;

import com.findpet.findpet_backend.adocao.anums.StatusAdocao;
import com.findpet.findpet_backend.adocao.model.Adocao;
import com.findpet.findpet_backend.adocao.respository.AdocaoRepository;
import com.findpet.findpet_backend.animal.enums.StatusAnimal;
import com.findpet.findpet_backend.animal.model.Animal;
import com.findpet.findpet_backend.animal.repository.AnimalRepository;
import com.findpet.findpet_backend.infrastructure.exception.BusinessException;
import com.findpet.findpet_backend.log_sistema.enums.AcaoLogSistema;
import com.findpet.findpet_backend.log_sistema.model.LogSistema;
import com.findpet.findpet_backend.log_sistema.repository.LogSistemaRepository;
import com.findpet.findpet_backend.usuario.model.Usuario;
import com.findpet.findpet_backend.usuario.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Regras da adoção responsável: um animal só pode ter uma solicitação em
 * aberto por vez, o dono do animal não pode adotar o próprio animal, e o
 * status do animal é sincronizado automaticamente com o andamento da adoção.
 */
@Service
@Transactional(readOnly = true)
public class AdocaoService {

    private static final List<StatusAdocao> STATUS_ABERTOS = List.of(
            StatusAdocao.SOLICITADA, StatusAdocao.EM_ANALISE
    );

    private static final List<StatusAdocao> STATUS_FINAIS = List.of(
            StatusAdocao.APROVADA, StatusAdocao.RECUSADA, StatusAdocao.CANCELADA
    );

    private final AdocaoRepository adocaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AnimalRepository animalRepository;
    private final LogSistemaRepository logSistemaRepository;

    public AdocaoService(
            AdocaoRepository adocaoRepository,
            UsuarioRepository usuarioRepository,
            AnimalRepository animalRepository,
            LogSistemaRepository logSistemaRepository
    ) {
        this.adocaoRepository = adocaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.animalRepository = animalRepository;
        this.logSistemaRepository = logSistemaRepository;
    }

    @Transactional
    public Adocao solicitar(Long usuarioId, Long animalId, String observacao) {
        Usuario usuario = buscarUsuarioPorId(usuarioId);
        Animal animal = buscarAnimalPorId(animalId);

        if (animal.getStatus() != StatusAnimal.DISPONIVEL) {
            throw new BusinessException("Este animal não está disponível para adoção.");
        }

        if (animal.getUsuario().getId().equals(usuarioId)) {
            throw new BusinessException("Você não pode solicitar a adoção do seu próprio animal.");
        }

        if (adocaoRepository.existsByUsuarioIdAndAnimalIdAndStatusIn(usuarioId, animalId, STATUS_ABERTOS)) {
            throw new BusinessException("Você já possui uma solicitação em aberto para este animal.");
        }

        Adocao adocao = new Adocao();
        adocao.setUsuario(usuario);
        adocao.setAnimal(animal);
        adocao.setObservacao(observacao);
        adocao.setStatus(StatusAdocao.SOLICITADA);
        adocao.setDataSolicitacao(LocalDateTime.now());

        Adocao adocaoSalva = adocaoRepository.save(adocao);

        animal.setStatus(StatusAnimal.EM_PROCESSO);
        animalRepository.save(animal);

        registrarLog(usuario, AcaoLogSistema.SOLICITACAO_ADOCAO,
                "Adoção do animal '" + animal.getNome() + "' solicitada.");

        return adocaoSalva;
    }

    public Page<Adocao> listarTodos(Pageable pageable) {
        return adocaoRepository.findAll(pageable);
    }

    public Adocao buscarPorId(Long id) {
        return adocaoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Solicitação de adoção não encontrada."));
    }

    @Transactional
    public Adocao atualizarStatus(Long id, StatusAdocao novoStatus) {
        Adocao adocao = buscarPorId(id);

        if (STATUS_FINAIS.contains(adocao.getStatus())) {
            throw new BusinessException("Esta solicitação de adoção já foi concluída e não pode ser alterada.");
        }

        if (novoStatus == StatusAdocao.SOLICITADA) {
            throw new BusinessException("Não é possível retornar uma adoção para o status inicial.");
        }

        Animal animal = adocao.getAnimal();

        adocao.setStatus(novoStatus);

        if (STATUS_FINAIS.contains(novoStatus)) {
            adocao.setDataConclusao(LocalDateTime.now());
            animal.setStatus(novoStatus == StatusAdocao.APROVADA ? StatusAnimal.ADOTADO : StatusAnimal.DISPONIVEL);
            animalRepository.save(animal);
        }

        Adocao adocaoAtualizada = adocaoRepository.save(adocao);

        registrarLog(adocao.getUsuario(), AcaoLogSistema.ATUALIZACAO_ADOCAO,
                "Adoção do animal '" + animal.getNome() + "' atualizada para " + novoStatus + ".");

        return adocaoAtualizada;
    }

    @Transactional
    public void excluir(Long id) {
        Adocao adocao = buscarPorId(id);

        if (adocao.getStatus() != StatusAdocao.SOLICITADA) {
            throw new BusinessException("Somente solicitações pendentes podem ser excluídas. Utilize a atualização de status para cancelar.");
        }

        Animal animal = adocao.getAnimal();
        animal.setStatus(StatusAnimal.DISPONIVEL);
        animalRepository.save(animal);

        adocaoRepository.delete(adocao);
    }

    private Usuario buscarUsuarioPorId(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));
    }

    private Animal buscarAnimalPorId(Long animalId) {
        return animalRepository.findById(animalId)
                .orElseThrow(() -> new BusinessException("Animal não encontrado."));
    }

    private void registrarLog(Usuario usuario, AcaoLogSistema acao, String descricao) {
        LogSistema log = new LogSistema();
        log.setUsuario(usuario);
        log.setAcao(acao);
        log.setDescricao(descricao);

        logSistemaRepository.save(log);
    }
}
