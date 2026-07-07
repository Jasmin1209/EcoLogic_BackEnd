package br.com.ifba.ecologic_back_end.modulos.relatorio.mapper;

import br.com.ifba.ecologic_back_end.modulos.relatorio.dto.request.RelatorioRequestDto;
import br.com.ifba.ecologic_back_end.modulos.relatorio.dto.response.RelatorioResponseDto;
import br.com.ifba.ecologic_back_end.modulos.relatorio.entity.Relatorio;
import org.springframework.stereotype.Component;

@Component
public class RelatorioMapper {

    // Converte RequestDTO para Entity
    public Relatorio toEntity(RelatorioRequestDto dto) {

        Relatorio relatorio = new Relatorio();

        relatorio.setTipoRelatorio(dto.getTipoRelatorio());
        relatorio.setPeriodoInicio(dto.getPeriodoInicio());
        relatorio.setPeriodoFim(dto.getPeriodoFim());

        return relatorio;
    }

    // Converte Entity para ResponseDTO
    public RelatorioResponseDto toResponseDTO(Relatorio relatorio) {

        if (relatorio == null) {
            return null;
        }

        RelatorioResponseDto dto = new RelatorioResponseDto();

        // Identificador (UUID) herdado de PersistenceEntity
        dto.setId(relatorio.getId());

        dto.setTitulo(relatorio.getTitulo());
        dto.setTipoRelatorio(relatorio.getTipoRelatorio());
        dto.setDataGeracao(relatorio.getDataGeracao());
        dto.setPeriodoInicio(relatorio.getPeriodoInicio());
        dto.setPeriodoFim(relatorio.getPeriodoFim());

        if (relatorio.getSetor() != null) {
            dto.setSetorId(relatorio.getSetor().getId());
            dto.setNomeSetor(relatorio.getSetor().getNome());
            if (relatorio.getSetor().getAdministrador() != null) {
                dto.setAdministradorId(relatorio.getSetor().getAdministrador().getId());
                dto.setAdministradorNome(relatorio.getSetor().getAdministrador().getNome());
            }
        }

        return dto;
    }
}