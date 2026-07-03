package com.findpet.findpet_backend.usuario.controller;

import com.findpet.findpet_backend.usuario.dto.LoginRequestDTO;
import com.findpet.findpet_backend.usuario.dto.UsuarioRequestDTO;
import com.findpet.findpet_backend.usuario.dto.UsuarioResponseDTO;
import com.findpet.findpet_backend.infrastructure.mapper.ObjectMapperUtil;
import com.findpet.findpet_backend.usuario.model.Usuario;
import com.findpet.findpet_backend.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

/*
 * Controller responsável por expor os endpoints REST de usuário.
 * Recebe as requisições HTTP e direciona as operações para o service.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    /*
    * Service com as regras de negócio e utilitário para converter entidades em DTOs.
    */
    private final UsuarioService usuarioService;
    private final ObjectMapperUtil objectMapperUtil;

    public UsuarioController(
            UsuarioService usuarioService,
            ObjectMapperUtil objectMapperUtil
    ) {
        this.usuarioService = usuarioService;
        this.objectMapperUtil = objectMapperUtil;
    }


        /*
        * Endpoint para cadastrar um novo usuário.
        * Recebe um DTO de entrada e retorna um DTO de resposta sem expor a senha.
        */
        @PostMapping("/cadastro")
        public ResponseEntity<UsuarioResponseDTO> cadastrar(
                @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO
        ) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequestDTO.getNome());
        usuario.setEmail(usuarioRequestDTO.getEmail());
        usuario.setSenha(usuarioRequestDTO.getSenha());

        Usuario usuarioSalvo = usuarioService.cadastrar(
                usuario,
                usuarioRequestDTO.getPerfilId()
        );

        UsuarioResponseDTO responseDTO = converterParaResponse(usuarioSalvo);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }


        /*
        * Endpoint para autenticar o usuário.
        * Recebe email e senha e retorna os dados públicos do usuário autenticado.
        */
        @PostMapping("/login")
        public ResponseEntity<UsuarioResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        Usuario usuario = usuarioService.login(
                loginRequestDTO.getEmail(),
                loginRequestDTO.getSenha()
        );

        UsuarioResponseDTO responseDTO = converterParaResponse(usuario);

        return ResponseEntity.ok(responseDTO);
    }

        /*
        * Endpoint para listar usuários de forma paginada.
        * Retorna uma página de DTOs sem informações sensíveis.
        */
        @GetMapping
        public ResponseEntity<Page<UsuarioResponseDTO>> listarTodos(
                @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC)
                Pageable pageable
        ) {
       
                Page<UsuarioResponseDTO> usuarios = usuarioService.listarTodos(pageable)
        .map(this::converterParaResponse);

        return ResponseEntity.ok(usuarios);
        }

        /*
        * Endpoint para buscar um usuário específico pelo ID.
        */
        @GetMapping("/{id}")
        public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
                Usuario usuario = usuarioService.buscarPorId(id);

        UsuarioResponseDTO responseDTO = converterParaResponse(usuario);

                return ResponseEntity.ok(responseDTO);
        }

        /*
        * Endpoint para atualizar os dados de um usuário existente.
        */
        @PutMapping("/{id}")
        public ResponseEntity<UsuarioResponseDTO> atualizar(
                @PathVariable Long id,
                @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO
        ) {
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome(usuarioRequestDTO.getNome());
        usuarioAtualizado.setEmail(usuarioRequestDTO.getEmail());
        usuarioAtualizado.setSenha(usuarioRequestDTO.getSenha());

        Usuario usuario = usuarioService.atualizar(
                id,
                usuarioAtualizado,
                usuarioRequestDTO.getPerfilId()
        );

        UsuarioResponseDTO responseDTO = converterParaResponse(usuario);

        return ResponseEntity.ok(responseDTO);
        }

        /*
        * Endpoint para excluir um usuário pelo ID.
        */
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> excluir(@PathVariable Long id) {
                usuarioService.excluir(id);

                return ResponseEntity.noContent().build();
        }
       

        private UsuarioResponseDTO converterParaResponse(Usuario usuario) {
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();

        responseDTO.setId(usuario.getId());
        responseDTO.setNome(usuario.getNome());
        responseDTO.setEmail(usuario.getEmail());
        responseDTO.setAtivo(usuario.getAtivo());
        responseDTO.setDataCriacao(usuario.getDataCriacao());

        if (usuario.getPerfil() != null) {
                responseDTO.setPerfilId(usuario.getPerfil().getId());
                responseDTO.setPerfilNome(usuario.getPerfil().getNome());
        }

        return responseDTO;
}

}