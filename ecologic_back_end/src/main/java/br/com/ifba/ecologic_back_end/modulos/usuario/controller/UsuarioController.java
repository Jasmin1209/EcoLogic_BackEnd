package br.com.ifba.ecologic_back_end.modulos.usuario.controller;

import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioAdministradorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioDiretorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioUpdateRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioAdministradorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioDiretorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.service.UsuarioIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

/**
 * Controller REST para o módulo Usuário.
 * Gerencia todas as operações CRUD (Create, Read, Update, Delete) de usuários.
 */
@CrossOrigin(origins = "http://localhost:5173") //Permite requisições do frontend em desenvolvimento
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioIService usuarioIService;

    // ========================
    // CRIAR (CREATE)
    // ========================

    /**
     * Cria um novo usuário administrador.
     * POST /api/v1/usuarios/administradores
     */
    @PostMapping("/administradores")
    public ResponseEntity<UsuarioAdministradorResponseDTO> criarAdministrador(
            @RequestBody @Valid UsuarioAdministradorRequestDTO dto) {
        UsuarioAdministradorResponseDTO response = usuarioIService.criarAdministrador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Cria um novo usuário diretor.
     * POST /api/v1/usuarios/diretores
     */
    @PostMapping("/diretores")
    public ResponseEntity<UsuarioDiretorResponseDTO> criarDiretor(
            @RequestBody @Valid UsuarioDiretorRequestDTO dto) {
        UsuarioDiretorResponseDTO response = usuarioIService.criarDiretor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ========================
    // OBTER (READ)
    // ========================

    /**
     * Obtém um usuário pelo nome (independente do tipo).
     * GET /api/v1/usuarios/{nome}
     */
    @GetMapping("/{nome}")
    public ResponseEntity<UsuarioResponseDTO> getUsuario(@PathVariable String nome) {
        UsuarioResponseDTO response = usuarioIService.getUsuario(nome);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtém um usuário pelo id.
     * GET /api/v1/usuarios/id/{id}
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioPorId(@PathVariable UUID id) {
        UsuarioResponseDTO response = usuarioIService.getUsuarioPorId(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos os usuários.
     * GET /api/v1/usuarios
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> response = usuarioIService.listarTodos();
        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos os usuários administradores.
     * GET /api/v1/usuarios/tipo/administradores
     */
    @GetMapping("/tipo/administradores")
    public ResponseEntity<List<UsuarioAdministradorResponseDTO>> listarAdministradores() {
        List<UsuarioAdministradorResponseDTO> response = usuarioIService.listarAdministradores();
        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos os usuários diretores.
     * GET /api/v1/usuarios/tipo/diretores
     */
    @GetMapping("/tipo/diretores")
    public ResponseEntity<List<UsuarioDiretorResponseDTO>> listarDiretores() {
        List<UsuarioDiretorResponseDTO> response = usuarioIService.listarDiretores();
        return ResponseEntity.ok(response);
    }

    // ========================
    // ATUALIZAR (UPDATE)
    // ========================

    /**
     * Atualiza um usuário pelo nome (independente do tipo).
     * PUT /api/v1/usuarios/{nome}
     */
    @PutMapping("/{nome}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @PathVariable String nome,
            @RequestBody @Valid UsuarioUpdateRequestDTO dto) {
        UsuarioResponseDTO response = usuarioIService.atualizarUsuario(nome, dto);
        return ResponseEntity.ok(response);
    }

    // ========================
    // DELETAR (DELETE)
    // ========================

    /**
     * Deleta um usuário pelo nome (independente do tipo).
     * DELETE /api/v1/usuarios/{nome}
     */
    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String nome) {
        usuarioIService.deletarUsuario(nome);
        return ResponseEntity.noContent().build();
    }
}
