package br.com.ifba.ecologic_back_end.modulos.estatistica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticaProdutoDTO {
    private Double media;
    private Double mediana;
}
