package br.com.ifba.ecologic_back_end.modulos.setor.controller;

import br.com.ifba.ecologic_back_end.modulos.setor.dto.SetorRequestDto;
import br.com.ifba.ecologic_back_end.modulos.setor.dto.SetorResponseDto;
import br.com.ifba.ecologic_back_end.modulos.setor.service.SetorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/setores")
@CrossOrigin(origins = "*") // Evita o bloqueio de requisições (CORS) quando o front-end tentar se conectar
@RequiredArgsConstructor // Injeta automaticamente o SetorService via construtor em tempo de compilação
public class SetorController {

    private final SetorService setorService;

    @PostMapping
    // @Valid: Intercepta a requisição e valida o DTO antes de executar o método. Se falhar, barra aqui.
    public ResponseEntity<SetorResponseDto> criar(@RequestBody @Valid SetorRequestDto request) {
        SetorResponseDto response = setorService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SetorResponseDto>> buscarTodos() {
        List<SetorResponseDto> setores = setorService.buscarTodos();
        return ResponseEntity.ok(setores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SetorResponseDto> buscarPorId(@PathVariable Long id) {
        SetorResponseDto response = setorService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorResponseDto> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid SetorRequestDto request) {
        SetorResponseDto response = setorService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    // Retorna HTTP 204 (No Content), que é o padrão sem corpo de resposta para deleções com sucesso
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        setorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}