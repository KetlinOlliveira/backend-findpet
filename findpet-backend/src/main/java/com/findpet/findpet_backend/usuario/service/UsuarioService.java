package com.findpet.findpet_backend.usuario.service;

import com.findpet.findpet_backend.infrastructure.exception.BusinessException;
import com.findpet.findpet_backend.infrastructure.security.UsuarioAutenticado;
import com.findpet.findpet_backend.usuario.model.Usuario;
import com.findpet.findpet_backend.usuario.repository.UsuarioRepository;
import com.findpet.findpet_backend.perfil.model.Perfil;
import com.findpet.findpet_backend.perfil.repository.PerfilRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    /*
    * Injeta o repository para permitir o acesso aos dados dos usuários.
    */
    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PerfilRepository perfilRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }

    /*
     * Realiza o login do usuário.
     * Delega ao AuthenticationManager do Spring Security, que busca o
     * usuário (via UsuarioDetailsServiceImpl) e compara a senha informada
     * com o hash salvo no banco (via PasswordEncoder). Credenciais erradas
     * lançam BadCredentialsException, tratada de forma genérica pelo
     * ApiExceptionHandler — isso evita revelar se o email existe ou não.
     */
    public Usuario login(String email, String senha) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha)
        );

        return ((UsuarioAutenticado) authentication.getPrincipal()).getUsuario();
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
        usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));

        if (perfilId != null) {
            Perfil perfil = buscarPerfilPorId(perfilId);
            usuario.setPerfil(perfil);
        }

        return usuarioRepository.save(usuario);
    }


    /*
    * Atualiza só a foto de perfil do usuário, sem precisar dos outros campos
    * (nome/email/senha) nem confirmar a senha de novo.
    */
    @Transactional
    public Usuario atualizarFoto(Long id, String fotoUrl) {
        Usuario usuario = buscarPorId(id);

        usuario.setFotoUrl(fotoUrl);

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