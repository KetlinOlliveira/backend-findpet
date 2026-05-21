package com.findpet.findpet_backend.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.findpet.findpet_backend.usuario.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}