package com.findpet.findpet_backend.infrastructure.exception;

import com.findpet.findpet_backend.infrastructure.dto.ErrorResponseDTO;
import com.findpet.findpet_backend.infrastructure.exception.FieldErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/*
 * Classe responsável por capturar e tratar exceções da API.
 * Centraliza as respostas de erro para evitar try/catch repetido nos controllers.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /*
     * Trata exceções de regra de negócio lançadas pelos services.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessException(
            BusinessException exception,
            HttpServletRequest request
    ) {
        ErrorResponseDTO erro = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Regra de negócio",
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    /*
     * Trata erros de validação gerados por campos inválidos nos DTOs.
     * Retorna cada campo que falhou junto com a mensagem da validação.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        List<FieldErrorDTO> campos = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldErrorDTO(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList();

        ErrorResponseDTO erro = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                "Existem campos inválidos na requisição.",
                request.getRequestURI(),
                campos
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    /*
     * Trata erros relacionados à integridade dos dados no banco.
     * Exemplo: tentativa de cadastrar dados duplicados.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception,
            HttpServletRequest request
    ) {
        ErrorResponseDTO erro = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de integridade dos dados",
                "Não foi possível concluir a operação. Verifique se os dados já existem no sistema.",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    /*
     * Trata qualquer erro inesperado não capturado pelos outros métodos.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(
            Exception exception,
            HttpServletRequest request
    ) {
        ErrorResponseDTO erro = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno",
                "Ocorreu um erro inesperado no servidor.",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}