package br.com.ifba.ecologic_back_end.modulos.produto.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.UUID;

@Data
public class ProdutoRequestDTO {
    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    @NotBlank(message = "Selecione uma categoria")
    private UUID categoriaId;

    @PositiveOrZero
    private Integer quantidade;

    @Positive
    private double custoUnitario;
}
