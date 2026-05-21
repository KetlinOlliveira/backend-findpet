package com.findpet.findpet_backend.usuario.service;

import com.findpet.findpet_backend.infrastructure.exception.BusinessException;
import com.findpet.findpet_backend.usuario.model.Usuario;
import com.findpet.findpet_backend.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;


/*
 * Service responsável pelas regras de negócio relacionadas ao usuário.
 * Aqui ficam validações como email duplicado, login e atualização de cadastro.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    /*
    * Injeta o repository para permitir o acesso aos dados dos usuários.
    */
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    /*
    * Cadastra um novo usuário.
    * Antes de salvar, verifica se o email já está cadastrado.
    */
    public Usuario cadastrar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new BusinessException("Email já cadastrado.");
        }

        return usuarioRepository.save(usuario);
    }
    /*
    * Realiza o login do usuário.
    * Busca pelo email e valida se a senha informada está correta.
    */
    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Email não encontrado."));

        if (!usuario.getSenha().equals(senha)) {
            throw new BusinessException("Senha incorreta.");
        }

        return usuario;
    }


    /*
    * Retorna todos os usuários cadastrados no banco.
    */
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
/*
 * Busca um usuário pelo ID.
 * Caso não encontre, lança uma exceção de negócio.
 */
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));
    }


/*
 * Atualiza os dados de um usuário existente.
 * Também impede que o email seja alterado para um email já usado por outro usuário.
 */
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


/*
 * Exclui um usuário pelo ID.
 * Antes de excluir, verifica se o usuário existe.
 */
    public void excluir(Long id) {
        Usuario usuario = buscarPorId(id);

        usuarioRepository.delete(usuario);
    }
}