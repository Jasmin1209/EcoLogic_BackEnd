package br.com.ifba.ecologic_back_end.modulos.relatorio.controller;

import br.com.ifba.ecologic_back_end.modulos.relatorio.dto.RelatorioRequestDto;
import br.com.ifba.ecologic_back_end.modulos.relatorio.dto.RelatorioResponseDto;
import br.com.ifba.ecologic_back_end.modulos.relatorio.service.RelatorioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @PathVariable Long id) {

        return ResponseEntity.ok(
                relatorioService.buscarPorId(id)
        );
    }

    // Remove um relatório
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        relatorioService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    // Atualiza um relatório
    @PutMapping("/{id}")
    public ResponseEntity<RelatorioResponseDto> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid RelatorioRequestDto dto) {

        return ResponseEntity.ok(
                relatorioService.atualizar(id, dto)
        );
    }
}