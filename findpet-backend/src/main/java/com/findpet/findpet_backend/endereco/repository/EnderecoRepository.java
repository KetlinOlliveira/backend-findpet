package com.findpet.findpet_backend.endereco.repository;

import com.findpet.findpet_backend.endereco.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}