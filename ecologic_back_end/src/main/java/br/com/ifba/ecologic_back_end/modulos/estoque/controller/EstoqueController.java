package br.com.ifba.ecologic_back_end.modulos.estoque.controller;

import br.com.ifba.ecologic_back_end.modulos.estoque.dto.request.EstoqueRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.estoque.dto.response.EstoqueResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.estoque.service.EstoqueIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueIService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstoqueResponseDTO salvar(
            @RequestBody EstoqueRequestDTO dto
    ) {

        return service.salvar(dto);
    }

    @GetMapping
    public List<EstoqueResponseDTO> listar() {

        return service.listar();
    }
}
