package br.com.ifba.ecologic_back_end.modulos.relatorio.dto.response;
import br.com.ifba.ecologic_back_end.modulos.relatorio.entity.TipoRelatorio;
import br.com.ifba.ecologic_back_end.modulos.consumo.dto.response.ConsumoResponseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class RelatorioResponseDto {

    // Relatório agora utiliza UUID (herdado de PersistenceEntity)
    private UUID id;

    private String titulo;

    private TipoRelatorio tipoRelatorio;

    private LocalDateTime dataGeracao;

    private LocalDateTime periodoInicio;

    private LocalDateTime periodoFim;

    /*
     * Dados do setor retornados para facilitar a exibição no frontend.
     * OBS: o `Setor` na aplicação ainda utiliza `Long` como id.
     */
    private Long setorId;

    private String nomeSetor;

    // Dados do administrador (usuário) do setor, quando aplicável
    private UUID administradorId;
    private String administradorNome;

    // Lista detalhada dos consumos que compõem o relatório
    private List<ConsumoResponseDTO> consumos;

    // Totais calculados para o relatório
    private Integer totalQuantidade;
    private Double custoTotal;
}