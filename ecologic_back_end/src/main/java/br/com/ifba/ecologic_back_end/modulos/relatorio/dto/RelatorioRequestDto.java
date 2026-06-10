package br.com.ifba.ecologic_back_end.modulos.relatorio.dto;

import br.com.ifba.ecologic_back_end.modulos.relatorio.entity.TipoRelatorio;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RelatorioRequestDto {

    @NotNull(message = "O tipo do relatório é obrigatório.")
    private TipoRelatorio tipoRelatorio;

    @NotNull(message = "A data inicial do período é obrigatória.")
    private LocalDateTime periodoInicio;

    @NotNull(message = "A data final do período é obrigatória.")
    private LocalDateTime periodoFim;

    /*
     * Para relatórios por setor.
     * Pode ser nulo quando o relatório for do tipo GERAL.
     */
    private Long setorId;
}