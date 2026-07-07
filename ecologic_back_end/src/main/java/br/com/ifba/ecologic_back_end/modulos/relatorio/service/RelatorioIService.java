package br.com.ifba.ecologic_back_end.modulos.relatorio.service;

import br.com.ifba.ecologic_back_end.modulos.relatorio.dto.request.RelatorioRequestDto;
import br.com.ifba.ecologic_back_end.modulos.relatorio.dto.response.RelatorioResponseDto;

import java.util.List;
import java.util.UUID;

public interface RelatorioIService {

    RelatorioResponseDto gerarRelatorio(RelatorioRequestDto dto);

    List<RelatorioResponseDto> listarTodos();

    RelatorioResponseDto buscarPorId(UUID id);

    void deletar(UUID id);

    RelatorioResponseDto atualizar(UUID id, RelatorioRequestDto dto);
}