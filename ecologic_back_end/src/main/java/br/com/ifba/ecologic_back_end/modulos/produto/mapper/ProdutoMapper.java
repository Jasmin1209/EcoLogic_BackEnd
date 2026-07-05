package br.com.ifba.ecologic_back_end.modulos.produto.mapper;

import br.com.ifba.ecologic_back_end.modulos.produto.dto.request.ProdutoRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.produto.dto.response.ProdutoResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.produto.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
    public Produto toEntity(ProdutoRequestDTO dto) {

        Produto produto = new Produto();

        produto.setNome(dto.getNome());
        produto.setCategoria(dto.getCategoria());
        produto.setQuantidade(dto.getQuantidade());
        produto.setCustoUnitario(dto.getCustoUnitario());

        return produto;
    }

    public ProdutoResponseDTO toResponseDTO(Produto entity) {

        ProdutoResponseDTO dto = new ProdutoResponseDTO();

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCategoria(entity.getCategoria());
        dto.setQuantidade(entity.getQuantidade());
        dto.setCustoUnitario(entity.getCustoUnitario());

        return dto;
    }

    public void updateEntity(
            Produto produto,
            ProdutoRequestDTO dto
    ) {

        produto.setNome(dto.getNome());
        produto.setCategoria(dto.getCategoria());
        produto.setQuantidade(dto.getQuantidade());
        produto.setCustoUnitario(dto.getCustoUnitario());
    }
}
