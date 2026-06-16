package com.findpet.findpet_backend.pessoa.repository;

import com.findpet.findpet_backend.pessoa.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    boolean existsByCpf(String cpf);

    Optional<Pessoa> findByCpf(String cpf);

    Optional<Pessoa> findByUsuarioId(Long usuarioId);

    Optional<Pessoa> findByEnderecoId(Long enderecoId);
}