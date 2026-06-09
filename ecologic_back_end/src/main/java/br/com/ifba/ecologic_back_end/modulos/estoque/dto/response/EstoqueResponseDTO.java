package br.com.ifba.ecologic_back_end.modulos.estoque.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EstoqueResponseDTO {

    private UUID id;

    private Integer quantidadeAtual;

    private Integer quantidadeMinima;

    private UUID produtoId;

    private String nomeProduto;
}