package br.com.ifba.ecologic_back_end.modulos.estatistica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuartilSetorDTO {
    // Gasto total do setor analisado
    private Double gastoTotal;
    // Classificação do setor: "BAIXO", "NORMAL" ou "ALTO"
    private String classificacao;
    // Valor do Q1 (limite dos 25% menores)
    private Double q1;
    // Valor do Q3 (limite dos 75%)
    private Double q3;
}
