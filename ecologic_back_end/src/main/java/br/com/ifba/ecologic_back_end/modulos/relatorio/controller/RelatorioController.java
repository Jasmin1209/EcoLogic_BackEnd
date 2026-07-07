package br.com.ifba.ecologic_back_end.modulos.relatorio.controller;

import br.com.ifba.ecologic_back_end.modulos.relatorio.dto.request.RelatorioRequestDto;
import br.com.ifba.ecologic_back_end.modulos.relatorio.dto.response.RelatorioResponseDto;
import br.com.ifba.ecologic_back_end.modulos.relatorio.service.RelatorioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    // Gera um novo relatório
    @PostMapping
    public ResponseEntity<RelatorioResponseDto> gerarRelatorio(
            @RequestBody @Valid RelatorioRequestDto dto) {

        RelatorioResponseDto relatorio =
                relatorioService.gerarRelatorio(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(relatorio);
    }

    // Lista todos os relatórios
    @GetMapping
    public ResponseEntity<List<RelatorioResponseDto>> listarTodos() {

        return ResponseEntity.ok(
                relatorioService.listarTodos()
        );
    }

    // Busca um relatório pelo id
    @GetMapping("/{id}")
    public ResponseEntity<RelatorioResponseDto> buscarPorId(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                relatorioService.buscarPorId(id)
        );
    }

    // Remove um relatório
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable UUID id) {

        relatorioService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    // Atualiza um relatório
    @PutMapping("/{id}")
    public ResponseEntity<RelatorioResponseDto> atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid RelatorioRequestDto dto) {

        return ResponseEntity.ok(
                relatorioService.atualizar(id, dto)
        );
    }
}