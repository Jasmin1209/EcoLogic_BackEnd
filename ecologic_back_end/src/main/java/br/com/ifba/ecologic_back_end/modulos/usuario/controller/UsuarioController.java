package br.com.ifba.ecologic_back_end.modulos.usuario.controller;

import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioAdministradorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioDiretorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioAdministradorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioDiretorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.service.UsuarioIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para o módulo Usuário.
 */
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioIService usuarioIService;

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
}