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
        Usuario usuario = objectMapperUtil.map(usuarioRequestDTO, Usuario.class);

        Usuario usuarioSalvo = usuarioService.cadastrar(usuario);

        UsuarioResponseDTO responseDTO = objectMapperUtil.map(
                usuarioSalvo,
                UsuarioResponseDTO.class
        );

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

        UsuarioResponseDTO responseDTO = objectMapperUtil.map(
                usuario,
                UsuarioResponseDTO.class
        );

        return ResponseEntity.ok(responseDTO);
    }
/*
 * Endpoint para listar todos os usuários cadastrados.
 * Retorna uma lista de DTOs sem informações sensíveis.
 */
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(
                objectMapperUtil.mapAll(
                        usuarioService.listarTodos(),
                        UsuarioResponseDTO.class
                )
        );
    }

/*
 * Endpoint para buscar um usuário específico pelo ID.
 */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);

        UsuarioResponseDTO responseDTO = objectMapperUtil.map(
                usuario,
                UsuarioResponseDTO.class
        );

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
        Usuario usuarioAtualizado = objectMapperUtil.map(
                usuarioRequestDTO,
                Usuario.class
        );

        Usuario usuario = usuarioService.atualizar(id, usuarioAtualizado);

        UsuarioResponseDTO responseDTO = objectMapperUtil.map(
                usuario,
                UsuarioResponseDTO.class
        );

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
}