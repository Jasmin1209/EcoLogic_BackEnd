package br.com.ifba.ecologic_back_end.modulos.setor.mapper;

import br.com.ifba.ecologic_back_end.modulos.setor.dto.SetorRequestDto;
import br.com.ifba.ecologic_back_end.modulos.setor.dto.SetorResponseDto;
import br.com.ifba.ecologic_back_end.modulos.setor.entity.Setor;
import org.springframework.stereotype.Component;

@Component // Permite que o Spring gerencie e injete esta classe no Service
public class SetorMapper {

    // Converte os dados que vieram do Front (DTO) para o formato do Banco (Entidade)
    public Setor toEntity(SetorRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Setor setor = new Setor();
        setor.setNome(dto.getNome());
        setor.setDescricao(dto.getDescricao());
        return setor;
    }

    // Converte os dados do Banco (Entidade) para o formato de resposta do Front (DTO)
    public SetorResponseDto toResponseDto(Setor setor) {
        if (setor == null) {
            return null;
        }

        SetorResponseDto dto = new SetorResponseDto();
        dto.setId(setor.getId());
        dto.setNome(setor.getNome());
        dto.setDescricao(setor.getDescricao());
        return dto;
    }
}