package br.com.ifba.ecologic_back_end.modulos.autenticacao.controller;

import br.com.ifba.ecologic_back_end.modulos.autenticacao.dto.LoginDTO;
import br.com.ifba.ecologic_back_end.modulos.autenticacao.dto.TokenResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.autenticacao.service.TokenService;
import br.com.ifba.ecologic_back_end.modulos.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Atualize a rota no hook do React para bater com essa
@CrossOrigin(origins = "http://localhost:5173")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> efetuarLogin(@RequestBody @Valid LoginDTO dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        // Verifica a senha no banco
        var authentication = manager.authenticate(authenticationToken);
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // Gera o token
        var tokenJWT = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new TokenResponseDTO(tokenJWT, usuario.getId()));
    }
}
