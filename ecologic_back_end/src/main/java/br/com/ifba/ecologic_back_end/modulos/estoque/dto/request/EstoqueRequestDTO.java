package br.com.ifba.ecologic_back_end.modulos.estoque.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EstoqueRequestDTO {

    @NotBlank(message = "A quantidade atual é obrigatório")
    private Integer quantidadeAtual;

    @NotBlank(message = "A quantidade atual é obrigatório")
    private Integer quantidadeMinima;

    private UUID produtoId;
}
