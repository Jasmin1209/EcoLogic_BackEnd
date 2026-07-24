package br.com.ifba.ecologic_back_end.modulos.estatistica.controller;

import br.com.ifba.ecologic_back_end.modulos.estatistica.dto.response.EstatisticaProdutoDTO;
import br.com.ifba.ecologic_back_end.modulos.estatistica.dto.response.ModaSetorDTO;
import br.com.ifba.ecologic_back_end.modulos.estatistica.dto.response.QuartilSetorDTO;
import br.com.ifba.ecologic_back_end.modulos.estatistica.service.EstatisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/estatisticas")
@RequiredArgsConstructor
public class EstatisticaController {

    private final EstatisticaService estatisticaService;

    // Retorna média e mediana das quantidades pedidas de um produto
    @GetMapping("/produto/{produtoId}")
    public EstatisticaProdutoDTO getEstatisticasProduto(@PathVariable UUID produtoId) {
        return estatisticaService.calcularEstatisticasProduto(produtoId);
    }

    // Retorna o setor que mais fez pedidos (Moda)
    @GetMapping("/setor/moda")
    public ModaSetorDTO getModaSetor() {
        return estatisticaService.calcularModaSetor();
    }

    // Retorna a classificação de gasto de um setor (Termômetro por Quartis)
    @GetMapping("/setor/{setorId}/quartil")
    public QuartilSetorDTO getQuartilSetor(@PathVariable Long setorId) {
        return estatisticaService.calcularQuartilSetor(setorId);
    }
}


