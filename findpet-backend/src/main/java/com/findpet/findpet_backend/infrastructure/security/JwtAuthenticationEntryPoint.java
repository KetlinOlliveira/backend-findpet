package com.findpet.findpet_backend.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findpet.findpet_backend.infrastructure.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
 * Chamado pelo Spring Security sempre que uma rota protegida é acessada
 * sem autenticação válida. Sem essa classe, o Spring devolveria um 401 em
 * HTML/texto puro; aqui garantimos o mesmo formato de erro usado pelo
 * ApiExceptionHandler no resto da API.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        ErrorResponseDTO erro = new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                "Não autenticado",
                "É necessário estar autenticado (token JWT ausente, inválido ou expirado).",
                request.getRequestURI()
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(erro));
    }
}
