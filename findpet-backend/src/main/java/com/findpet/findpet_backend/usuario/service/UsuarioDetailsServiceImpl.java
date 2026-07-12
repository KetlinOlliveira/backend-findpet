package com.findpet.findpet_backend.usuario.service;

import com.findpet.findpet_backend.infrastructure.security.UsuarioAutenticado;
import com.findpet.findpet_backend.usuario.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * Ponte entre o Spring Security e o banco de usuários da aplicação.
 * O Spring Security chama loadUserByUsername sempre que precisa autenticar
 * alguém (no login, via AuthenticationManager) ou revalidar um token JWT
 * (no JwtAuthenticationFilter). Aqui usamos o email como "username".
 */
@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .map(UsuarioAutenticado::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }
}
