package br.com.ifba.ecologic_back_end.modulos.relatorio.service;

import br.com.ifba.ecologic_back_end.modulos.relatorio.dto.RelatorioRequestDto;
import br.com.ifba.ecologic_back_end.modulos.relatorio.dto.RelatorioResponseDto;

import java.util.List;

public interface RelatorioIService {

    RelatorioResponseDto gerarRelatorio(RelatorioRequestDto dto);

    List<RelatorioResponseDto> listarTodos();

    RelatorioResponseDto buscarPorId(Long id);

    void deletar(Long id);

    RelatorioResponseDto atualizar(Long id, RelatorioRequestDto dto);
}