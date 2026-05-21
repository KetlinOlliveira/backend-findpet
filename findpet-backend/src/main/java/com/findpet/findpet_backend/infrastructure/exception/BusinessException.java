package com.findpet.findpet_backend.infrastructure.exception;


/*
 * Exceção personalizada para representar erros de regra de negócio.
 * É usada quando uma operação não pode ser concluída por validações do sistema.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable causa) {
        super(message, causa);
    }
    
}
