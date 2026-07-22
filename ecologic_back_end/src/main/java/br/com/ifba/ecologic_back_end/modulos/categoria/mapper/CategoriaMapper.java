package br.com.ifba.ecologic_back_end.modulos.categoria.mapper;

import br.com.ifba.ecologic_back_end.modulos.categoria.dto.CategoriaRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.categoria.dto.CategoriaResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.categoria.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    /**
     * Transforma a Entidade do banco de dados no DTO de Resposta para o React.
     */
    public CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }

        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(categoria.getId()); // Puxa o UUID automaticamente do PersistenceEntity
        dto.setNome(categoria.getNome());
        dto.setDescricao(categoria.getDescricao());

        return dto;
    }

    /**
     * Transforma o DTO enviado pelo React em uma Entidade pronta para ser salva no Banco.
     */
    public Categoria toEntity(CategoriaRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        return categoria;
    }
}