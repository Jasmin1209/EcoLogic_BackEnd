package br.com.ifba.ecologic_back_end.modulos.estatistica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModaSetorDTO {
    // Nome do setor mais gastão (que mais aparece nos pedidos)
    private String setorModa;
    // Quantidade de vezes que ele apareceu
    private Integer frequencia;
}
