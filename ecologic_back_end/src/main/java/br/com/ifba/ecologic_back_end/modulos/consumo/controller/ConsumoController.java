package br.com.ifba.ecologic_back_end.modulos.consumo.controller;

import br.com.ifba.ecologic_back_end.modulos.consumo.dto.request.ConsumoRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.consumo.dto.response.ConsumoResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.consumo.service.ConsumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumos")
@RequiredArgsConstructor
public class ConsumoController {

    private final ConsumoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsumoResponseDTO salvar(
            @RequestBody ConsumoRequestDTO dto
    ) {

        return service.salvar(dto);
    }

    @GetMapping
    public List<ConsumoResponseDTO> listar() {

        return service.listar();
    }
}