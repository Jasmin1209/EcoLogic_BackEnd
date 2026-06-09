package br.com.ifba.ecologic_back_end.modulos.estoque.service;

import br.com.ifba.ecologic_back_end.modulos.estoque.dto.request.EstoqueRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.estoque.dto.response.EstoqueResponseDTO;

import java.util.List;

public interface EstoqueIService {
    EstoqueResponseDTO salvar(EstoqueRequestDTO dto);

    List<EstoqueResponseDTO> listar();
}
