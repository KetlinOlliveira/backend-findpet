package com.findpet.findpet_backend.usuario.controller;

import com.findpet.findpet_backend.infrastructure.security.JwtService;
import com.findpet.findpet_backend.infrastructure.security.UsuarioAutenticado;
import com.findpet.findpet_backend.usuario.dto.LoginRequestDTO;
import com.findpet.findpet_backend.usuario.dto.LoginResponseDTO;
import com.findpet.findpet_backend.usuario.dto.UsuarioFotoRequestDTO;
import com.findpet.findpet_backend.usuario.dto.UsuarioRequestDTO;
import com.findpet.findpet_backend.usuario.dto.UsuarioResponseDTO;
import com.findpet.findpet_backend.infrastructure.mapper.ObjectMapperUtil;
import com.findpet.findpet_backend.usuario.model.Usuario;
import com.findpet.findpet_backend.usuario.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Controller responsável por expor os endpoints REST de usuário.
 * Recebe as requisições HTTP e direciona as operações para o service.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController implements IUsuarioController {

    /*
    * Service com as regras de negócio e utilitário para converter entidades em DTOs.
    */
    private final UsuarioService usuarioService;
    private final ObjectMapperUtil objectMapperUtil;
    private final JwtService jwtService;

    public UsuarioController(
            UsuarioService usuarioService,
            ObjectMapperUtil objectMapperUtil,
            JwtService jwtService
    ) {
        this.usuarioService = usuarioService;
        this.objectMapperUtil = objectMapperUtil;
        this.jwtService = jwtService;
    }

    /*
    * Cadastra um novo usuário.
    * Recebe um DTO de entrada e retorna um DTO de resposta sem expor a senha.
    */
    @Override
    public ResponseEntity<UsuarioResponseDTO> cadastrar(UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequestDTO.getNome());
        usuario.setEmail(usuarioRequestDTO.getEmail());
        usuario.setSenha(usuarioRequestDTO.getSenha());

        Usuario usuarioSalvo = usuarioService.cadastrar(
                usuario,
                usuarioRequestDTO.getPerfilId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaResponse(usuarioSalvo));
    }

    /*
    * Autentica o usuário e gera um token JWT.
    * Recebe email e senha, valida as credenciais via UsuarioService e
    * devolve o token que o frontend deve reenviar nas próximas requisições.
    */
    @Override
    public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO) {
        Usuario usuario = usuarioService.login(
                loginRequestDTO.getEmail(),
                loginRequestDTO.getSenha()
        );

        String token = jwtService.gerarToken(new UsuarioAutenticado(usuario));

        return ResponseEntity.ok(new LoginResponseDTO(token, converterParaResponse(usuario)));
    }

    /*
    * Lista usuários de forma paginada.
    * Retorna uma página de DTOs sem informações sensíveis.
    */
    @Override
    public ResponseEntity<Page<UsuarioResponseDTO>> listarTodos(Pageable pageable) {
        Page<UsuarioResponseDTO> usuarios = usuarioService.listarTodos(pageable)
                .map(this::converterParaResponse);

        return ResponseEntity.ok(usuarios);
    }

    /*
    * Busca um usuário específico pelo ID.
    */
    @Override
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);

        return ResponseEntity.ok(converterParaResponse(usuario));
    }

    /*
    * Atualiza os dados de um usuário existente.
    */
    @Override
    public ResponseEntity<UsuarioResponseDTO> atualizar(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome(usuarioRequestDTO.getNome());
        usuarioAtualizado.setEmail(usuarioRequestDTO.getEmail());
        usuarioAtualizado.setSenha(usuarioRequestDTO.getSenha());

        Usuario usuario = usuarioService.atualizar(
                id,
                usuarioAtualizado,
                usuarioRequestDTO.getPerfilId()
        );

        return ResponseEntity.ok(converterParaResponse(usuario));
    }

    /*
    * Exclui um usuário pelo ID.
    */
    @Override
    public ResponseEntity<Void> excluir(Long id) {
        usuarioService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    /*
    * Atualiza a foto de perfil do usuário.
    */
    @Override
    public ResponseEntity<UsuarioResponseDTO> atualizarFoto(Long id, UsuarioFotoRequestDTO fotoRequestDTO) {
        Usuario usuario = usuarioService.atualizarFoto(id, fotoRequestDTO.getFotoUrl());

        return ResponseEntity.ok(converterParaResponse(usuario));
    }

    private UsuarioResponseDTO converterParaResponse(Usuario usuario) {
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();

        responseDTO.setId(usuario.getId());
        responseDTO.setNome(usuario.getNome());
        responseDTO.setEmail(usuario.getEmail());
        responseDTO.setAtivo(usuario.getAtivo());
        responseDTO.setFotoUrl(usuario.getFotoUrl());
        responseDTO.setDataCriacao(usuario.getDataCriacao());

        if (usuario.getPerfil() != null) {
            responseDTO.setPerfilId(usuario.getPerfil().getId());
            responseDTO.setPerfilNome(usuario.getPerfil().getNome());
        }

        return responseDTO;
    }
}
