package br.com.ifba.ecologic_back_end.modulos.produto.dto.request;

import br.com.ifba.ecologic_back_end.modulos.produto.entity.enums.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProdutoRequestDTO {
    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    @NotBlank(message = "Selecione uma categoria")
    private Categoria categoria;

    private String unidadeDeMedida;

    @Positive
    private double custoUnitario;
}
