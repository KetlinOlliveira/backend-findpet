package com.findpet.findpet_backend.usuario.service;

import com.findpet.findpet_backend.infrastructure.exception.BusinessException;
import com.findpet.findpet_backend.usuario.model.Usuario;
import com.findpet.findpet_backend.usuario.repository.UsuarioRepository;
import com.findpet.findpet_backend.perfil.model.Perfil;
import com.findpet.findpet_backend.perfil.repository.PerfilRepository;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/*
 * Service responsável pelas regras de negócio relacionadas ao usuário.
 * Aqui ficam validações como email duplicado, login e atualização de cadastro.
 */
@Service
@Transactional(readOnly = true)
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;


    /*  
    * Injeta o repository para permitir o acesso aos dados dos usuários.
    */
    public UsuarioService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }


    /*
    * Cadastra um novo usuário.
    * Antes de salvar, verifica se o email já está cadastrado.
    */
   
    @Transactional
    public Usuario cadastrar(Usuario usuario, Long perfilId) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new BusinessException("Email já cadastrado.");
        }

        if (perfilId != null) {
            Perfil perfil = buscarPerfilPorId(perfilId);
            usuario.setPerfil(perfil);
        }

        usuario.setId(null);
        usuario.setAtivo(true);

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
            throw new BusinessException("Senha inválid.");
        }

        return usuario;
    }

    /*
    * Retorna os usuários cadastrados de forma paginada.
    * O Pageable permite controlar página, quantidade de itens e ordenação.
    */
    public Page<Usuario> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
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
    @Transactional
    public Usuario atualizar(Long id, Usuario usuarioAtualizado, Long perfilId) {
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

        if (perfilId != null) {
            Perfil perfil = buscarPerfilPorId(perfilId);
            usuario.setPerfil(perfil);
        }

        return usuarioRepository.save(usuario);
    }


    /*
    * Exclui um usuário pelo ID.
    * Antes de excluir, verifica se o usuário existe.
    */
    @Transactional
    public void excluir(Long id) {
        Usuario usuario = buscarPorId(id);

        usuarioRepository.delete(usuario);
    }


    private Perfil buscarPerfilPorId(Long perfilId) {
        return perfilRepository.findById(perfilId)
                .orElseThrow(() -> new BusinessException("Perfil não encontrado."));
        }
}