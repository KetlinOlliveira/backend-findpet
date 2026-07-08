package com.findpet.findpet_backend.log_sistema.repository;

import com.findpet.findpet_backend.log_sistema.model.LogSistema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogSistemaRepository extends JpaRepository<LogSistema, Long> {

    Page<LogSistema> findByUsuarioId(Long usuarioId, Pageable pageable);
}
