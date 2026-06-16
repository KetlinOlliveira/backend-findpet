package com.findpet.findpet_backend.perfil.repository;

import com.findpet.findpet_backend.perfil.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    boolean existsByNome(String nome);
}