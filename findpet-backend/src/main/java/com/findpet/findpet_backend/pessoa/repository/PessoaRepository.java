package com.findpet.findpet_backend.pessoa.repository;

import com.findpet.findpet_backend.pessoa.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    boolean existsByCpf(String cpf);
}