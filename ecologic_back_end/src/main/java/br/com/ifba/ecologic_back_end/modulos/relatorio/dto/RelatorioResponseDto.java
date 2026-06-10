package br.com.ifba.ecologic_back_end.modulos.relatorio.dto;
import br.com.ifba.ecologic_back_end.modulos.relatorio.entity.TipoRelatorio;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RelatorioResponseDto {

    private Long id;

    private String titulo;

    private TipoRelatorio tipoRelatorio;

    private LocalDateTime dataGeracao;

    private LocalDateTime periodoInicio;

    private LocalDateTime periodoFim;

    /*
     * Dados do setor retornados para facilitar
     * a exibição no frontend.
     */
    private Long setorId;

    private String nomeSetor;
}