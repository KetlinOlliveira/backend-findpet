package com.findpet.findpet_backend.raca.repository;

import com.findpet.findpet_backend.raca.model.Raca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RacaRepository extends JpaRepository<Raca, Long> {

    boolean existsByNomeIgnoreCaseAndEspecieId(String nome, Long especieId);

    Optional<Raca> findByNomeIgnoreCaseAndEspecieId(String nome, Long especieId);

    boolean existsByEspecieId(Long especieId);
}
