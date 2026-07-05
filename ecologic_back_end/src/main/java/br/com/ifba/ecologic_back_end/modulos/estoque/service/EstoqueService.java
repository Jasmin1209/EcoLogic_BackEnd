package br.com.ifba.ecologic_back_end.modulos.estoque.service;

import br.com.ifba.ecologic_back_end.modulos.estoque.dto.request.EstoqueRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.estoque.dto.response.EstoqueResponseDTO;
import br.com.ifba.ecologic_back_end.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueService implements EstoqueIService {

    // Estoque module descontinuado — métodos irão lançar BusinessException

    @Override
    public EstoqueResponseDTO salvar(
            EstoqueRequestDTO dto
    ) {
        throw new BusinessException("Módulo 'estoque' descontinuado. Use produto.quantidade.");
    }

    @Override
    public List<EstoqueResponseDTO> listar() {
        throw new BusinessException("Módulo 'estoque' descontinuado.");
    }
}
