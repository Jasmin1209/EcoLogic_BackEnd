package br.com.ifba.ecologic_back_end.modulos.produto.dto.response;

import br.com.ifba.ecologic_back_end.modulos.produto.entity.enums.Categoria;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter

public class ProdutoResponseDTO {
    private UUID id;

    private String nome;

    private Categoria categoria;

    private Integer quantidade;

    private double custoUnitario;
}
