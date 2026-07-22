package br.com.ifba.ecologic_back_end.modulos.produto.mapper;

import br.com.ifba.ecologic_back_end.modulos.produto.dto.request.ProdutoRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.produto.dto.response.ProdutoResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.produto.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public ProdutoResponseDTO toResponseDTO(Produto produto) {
        if (produto == null) {
            return null;
        }

        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setQuantidade(produto.getQuantidade());
        dto.setCustoUnitario(produto.getCustoUnitario());

        // Proteção contra NullPointerException caso o produto não tenha categoria no banco
        if (produto.getCategoria() != null) {
            dto.setCategoriaId(produto.getCategoria().getId());
            dto.setCategoriaNome(produto.getCategoria().getNome());
        } else {
            dto.setCategoriaId(null);
            dto.setCategoriaNome("Sem Categoria");
        }

        return dto;
    }

    public Produto toEntity(ProdutoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setQuantidade(dto.getQuantidade());
        produto.setCustoUnitario(dto.getCustoUnitario());

        return produto;
    }

    /**
     * Atualiza os campos simples da entidade existente com os dados vindos do DTO.
     */
    public void updateEntity(ProdutoRequestDTO dto, Produto produto) {
        if (dto == null || produto == null) {
            return;
        }

        produto.setNome(dto.getNome());
        produto.setQuantidade(dto.getQuantidade());
        produto.setCustoUnitario(dto.getCustoUnitario());

        // A categoria será atualizada lá no ProdutoService através do categoriaId
    }
}