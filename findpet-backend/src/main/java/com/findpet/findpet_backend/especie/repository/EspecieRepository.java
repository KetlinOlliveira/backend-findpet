package com.findpet.findpet_backend.especie.repository;

import com.findpet.findpet_backend.especie.model.Especie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecieRepository extends JpaRepository<Especie, Long> {

    boolean existsByNomeIgnoreCase(String nome);

    Optional<Especie> findByNomeIgnoreCase(String nome);
}
