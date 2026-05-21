package com.findpet.findpet_backend.usuario.service;

import com.findpet.findpet_backend.infrastructure.exception.BusinessException;
import com.findpet.findpet_backend.usuario.model.Usuario;
import com.findpet.findpet_backend.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new BusinessException("Email já cadastrado.");
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Email não encontrado."));

        if (!usuario.getSenha().equals(senha)) {
            throw new BusinessException("Senha incorreta.");
        }

        return usuario;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = buscarPorId(id);

        usuarioRepository.findByEmail(usuarioAtualizado.getEmail())
                .ifPresent(usuarioComMesmoEmail -> {
                    if (!usuarioComMesmoEmail.getId().equals(id)) {
                        throw new BusinessException("Este email já está sendo usado por outro usuário.");
                    }
                });

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setSenha(usuarioAtualizado.getSenha());

        return usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
        Usuario usuario = buscarPorId(id);

        usuarioRepository.delete(usuario);
    }
}