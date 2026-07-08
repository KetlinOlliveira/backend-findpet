package com.findpet.findpet_backend.log_sistema.controller;

import com.findpet.findpet_backend.log_sistema.dto.LogSistemaRequestDTO;
import com.findpet.findpet_backend.log_sistema.dto.LogSistemaResponseDTO;
import com.findpet.findpet_backend.log_sistema.model.LogSistema;
import com.findpet.findpet_backend.log_sistema.service.LogSistemaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "*")
public class LogSistemaController implements ILogSistemaController {

    private final LogSistemaService logSistemaService;

    public LogSistemaController(LogSistemaService logSistemaService) {
        this.logSistemaService = logSistemaService;
    }

    @Override
    public ResponseEntity<LogSistemaResponseDTO> registrar(LogSistemaRequestDTO logSistemaRequestDTO) {
        LogSistema logSistema = new LogSistema();
        logSistema.setAcao(logSistemaRequestDTO.getAcao());
        logSistema.setDescricao(logSistemaRequestDTO.getDescricao());

        LogSistema logSalvo = logSistemaService.registrar(logSistema, logSistemaRequestDTO.getUsuarioId());

        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaResponse(logSalvo));
    }

    @Override
    public ResponseEntity<Page<LogSistemaResponseDTO>> listarTodos(Long usuarioId, Pageable pageable) {
        Page<LogSistema> logs = usuarioId != null
                ? logSistemaService.listarPorUsuario(usuarioId, pageable)
                : logSistemaService.listarTodos(pageable);

        return ResponseEntity.ok(logs.map(this::converterParaResponse));
    }

    @Override
    public ResponseEntity<LogSistemaResponseDTO> buscarPorId(Long id) {
        LogSistema logSistema = logSistemaService.buscarPorId(id);

        return ResponseEntity.ok(converterParaResponse(logSistema));
    }

    private LogSistemaResponseDTO converterParaResponse(LogSistema logSistema) {
        LogSistemaResponseDTO responseDTO = new LogSistemaResponseDTO();

        responseDTO.setId(logSistema.getId());
        responseDTO.setAcao(logSistema.getAcao());
        responseDTO.setDescricao(logSistema.getDescricao());
        responseDTO.setDataHora(logSistema.getDataHora());

        if (logSistema.getUsuario() != null) {
            responseDTO.setUsuarioId(logSistema.getUsuario().getId());
            responseDTO.setUsuarioNome(logSistema.getUsuario().getNome());
        }

        return responseDTO;
    }
}
