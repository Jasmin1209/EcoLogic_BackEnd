package br.com.ifba.ecologic_back_end.modulos.consumo.service;

import br.com.ifba.ecologic_back_end.modulos.consumo.dto.request.ConsumoRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.consumo.dto.response.ConsumoResponseDTO;

import java.util.List;

public interface ConsumoIService {
    ConsumoResponseDTO salvar(
            ConsumoRequestDTO dto
    );

    List<ConsumoResponseDTO> listar();
}
