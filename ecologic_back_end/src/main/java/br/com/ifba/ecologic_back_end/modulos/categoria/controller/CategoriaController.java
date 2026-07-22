package br.com.ifba.ecologic_back_end.modulos.categoria.controller;

import br.com.ifba.ecologic_back_end.modulos.categoria.dto.CategoriaRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.categoria.dto.CategoriaResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.categoria.service.CategoriaIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    // Injeção da INTERFACE ao invés da classe concreta
    private final CategoriaIService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody CategoriaRequestDTO dto) {
        try {
            CategoriaResponseDTO categoriaSalva = categoriaService.salvar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
        } catch (RuntimeException e) {
            // Retorna HTTP 400 em caso de erro de validação (ex: nome duplicado)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id, @RequestBody CategoriaRequestDTO dto) {
        try {
            CategoriaResponseDTO categoriaAtualizada = categoriaService.atualizar(id, dto);
            return ResponseEntity.ok(categoriaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable UUID id) {
        try {
            categoriaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Retorna HTTP 409 CONFLICT caso a categoria possua produtos vinculados
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}