package com.findpet.findpet_backend.service;


import com.findpet.findpet_backend.model.Usuario;
import com.findpet.findpet_backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario login(Usuario usuarioLogin) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail(usuarioLogin.getEmail());

        if (usuarioEncontrado.isEmpty()) {
            throw new RuntimeException("Email não encontrado.");
        }

        Usuario usuario = usuarioEncontrado.get();

        if (!usuario.getSenha().equals(usuarioLogin.getSenha())) {
            throw new RuntimeException("Senha incorreta.");
        }

        return usuario;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }
}