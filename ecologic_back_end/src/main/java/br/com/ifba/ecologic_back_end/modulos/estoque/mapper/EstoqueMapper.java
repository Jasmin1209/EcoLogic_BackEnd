package br.com.ifba.ecologic_back_end.modulos.estoque.mapper;

import br.com.ifba.ecologic_back_end.modulos.estoque.dto.response.EstoqueResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.estoque.entity.Estoque;
import org.springframework.stereotype.Component;

@Component
public class EstoqueMapper {

    public EstoqueResponseDTO toResponseDTO(Estoque entity) {

        EstoqueResponseDTO dto =
                new EstoqueResponseDTO();

        dto.setId(entity.getId());
        dto.setQuantidadeAtual(entity.getQuantidadeAtual());
        dto.setQuantidadeMinima(entity.getQuantidadeMinima());

        dto.setProdutoId(entity.getProduto().getId());
        dto.setNomeProduto(entity.getProduto().getNome());

        return dto;
    }
}
