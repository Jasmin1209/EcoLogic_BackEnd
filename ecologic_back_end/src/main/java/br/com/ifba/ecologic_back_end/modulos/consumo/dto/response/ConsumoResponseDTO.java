package br.com.ifba.ecologic_back_end.modulos.consumo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ConsumoResponseDTO {

    private UUID id;

    private Integer quantidade;

    private LocalDate dataRetirada;

    private String justificativa;

    private UUID produtoId;

    private String nomeProduto;

    // Dados do setor que realizou o consumo
    private Long setorId;

    private String nomeSetor;

    private Double custoTotal;
}