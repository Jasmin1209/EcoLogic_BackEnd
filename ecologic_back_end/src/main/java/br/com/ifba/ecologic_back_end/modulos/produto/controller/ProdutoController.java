package br.com.ifba.ecologic_back_end.modulos.produto.controller;

import java.util.List;
import java.util.UUID;

import br.com.ifba.ecologic_back_end.modulos.produto.service.ProdutoIService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import br.com.ifba.ecologic_back_end.modulos.produto.dto.request.ProdutoRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.produto.dto.response.ProdutoResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoIService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDTO salvar(
            @RequestBody ProdutoRequestDTO dto
    ) {

        return service.salvar(dto);
    }

    @GetMapping
    public List<ProdutoResponseDTO> listar() {

        return service.listar();
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarPorId(
            @PathVariable UUID id
    ) {

        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizar(
            @PathVariable UUID id,
            @RequestBody ProdutoRequestDTO dto
    ) {

        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @PathVariable UUID id
    ) {

        service.deletar(id);
    }
}