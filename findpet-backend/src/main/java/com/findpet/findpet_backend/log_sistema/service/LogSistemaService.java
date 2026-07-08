package com.findpet.findpet_backend.log_sistema.service;

import com.findpet.findpet_backend.infrastructure.exception.BusinessException;
import com.findpet.findpet_backend.log_sistema.model.LogSistema;
import com.findpet.findpet_backend.log_sistema.repository.LogSistemaRepository;
import com.findpet.findpet_backend.usuario.model.Usuario;
import com.findpet.findpet_backend.usuario.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * Os logs de sistema formam uma trilha de auditoria: por regra de negócio,
 * uma vez registrados não podem ser alterados nem excluídos, apenas
 * consultados.
 */
@Service
@Transactional(readOnly = true)
public class LogSistemaService {

    private final LogSistemaRepository logSistemaRepository;
    private final UsuarioRepository usuarioRepository;

    public LogSistemaService(
            LogSistemaRepository logSistemaRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.logSistemaRepository = logSistemaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public LogSistema registrar(LogSistema logSistema, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        logSistema.setUsuario(usuario);

        return logSistemaRepository.save(logSistema);
    }

    public Page<LogSistema> listarTodos(Pageable pageable) {
        return logSistemaRepository.findAll(pageable);
    }

    public Page<LogSistema> listarPorUsuario(Long usuarioId, Pageable pageable) {
        return logSistemaRepository.findByUsuarioId(usuarioId, pageable);
    }

    public LogSistema buscarPorId(Long id) {
        return logSistemaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Log não encontrado."));
    }
}
