package br.com.ifba.ecologic_back_end.modulos.consumo.mapper;

import br.com.ifba.ecologic_back_end.modulos.consumo.dto.response.ConsumoResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.consumo.entity.Consumo;
import org.springframework.stereotype.Component;

@Component
public class ConsumoMapper {

    public ConsumoResponseDTO toResponseDTO(
            Consumo entity
    ) {

        ConsumoResponseDTO dto =
                new ConsumoResponseDTO();

        dto.setId(entity.getId());
        dto.setQuantidade(entity.getQuantidade());
        dto.setDataRetirada(entity.getDataRetirada());
        dto.setJustificativa(entity.getJustificativa());

        dto.setProdutoId(entity.getProduto().getId());
        dto.setNomeProduto(entity.getProduto().getNome());
        if (entity.getSetor() != null) {
            dto.setSetorId(entity.getSetor().getId());
            dto.setNomeSetor(entity.getSetor().getNome());
        }

        return dto;
    }
}
