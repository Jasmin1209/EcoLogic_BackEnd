package br.com.ifba.ecologic_back_end.modulos.consumo.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ConsumoRequestDTO {

    private Integer quantidade;

    private String justificativa;

    private UUID produtoId;
}