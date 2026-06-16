package com.findpet.findpet_backend.perfil.repository;

import com.findpet.findpet_backend.perfil.model.Perfil;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
   boolean existsByNomeIgnoreCase(String nome);

    Optional<Perfil> findByNomeIgnoreCase(String nome);


}