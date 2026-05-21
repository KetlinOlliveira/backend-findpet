package com.findpet.findpet_backend.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.findpet.findpet_backend.usuario.model.Usuario;

import java.util.Optional;


/*
 * Repository responsável pelo acesso aos dados da entidade Usuario.
 * Herda métodos prontos do JpaRepository, como salvar, listar, buscar e excluir.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    /*
    * Busca um usuário pelo email.
    * Usado principalmente no login e na validação de email duplicado.
    */
    Optional<Usuario> findByEmail(String email);


    /*
    * Verifica se já existe um usuário cadastrado com o email informado.
    */
    boolean existsByEmail(String email);
}