package com.findpet.findpet_backend.adocao.respository;

import com.findpet.findpet_backend.adocao.anums.StatusAdocao;
import com.findpet.findpet_backend.adocao.model.Adocao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {

    boolean existsByAnimalIdAndStatusIn(Long animalId, List<StatusAdocao> status);

    boolean existsByUsuarioIdAndAnimalIdAndStatusIn(Long usuarioId, Long animalId, List<StatusAdocao> status);

    Optional<Adocao> findFirstByAnimalIdAndStatusIn(Long animalId, List<StatusAdocao> status);
}
