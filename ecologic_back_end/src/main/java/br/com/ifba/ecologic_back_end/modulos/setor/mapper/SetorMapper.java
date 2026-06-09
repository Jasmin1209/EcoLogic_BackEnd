package br.com.ifba.ecologic_back_end.modulos.setor.mapper;

import br.com.ifba.ecologic_back_end.modulos.setor.dto.SetorRequestDto;
import br.com.ifba.ecologic_back_end.modulos.setor.dto.SetorResponseDto;
import br.com.ifba.ecologic_back_end.modulos.setor.entity.Setor;
import br.com.ifba.ecologic_back_end.modulos.usuario.entity.UsuarioAdministrador;
import org.springframework.stereotype.Component;

@Component
public class SetorMapper {

    public Setor toEntity(SetorRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Setor setor = new Setor();
        setor.setNome(dto.getNome());
        setor.setDescricao(dto.getDescricao());

        // Instanciamos apenas o objeto com o ID para o Hibernate criar o relacionamento
        if (dto.getAdministradorId() != null) {
            UsuarioAdministrador admin = new UsuarioAdministrador();
            admin.setId(dto.getAdministradorId());
            setor.setAdministrador(admin);
        }

        return setor;
    }

    public SetorResponseDto toResponseDto(Setor setor) {
        if (setor == null) {
            return null;
        }

        SetorResponseDto dto = new SetorResponseDto();
        dto.setId(setor.getId());
        dto.setNome(setor.getNome());
        dto.setDescricao(setor.getDescricao());

        // Extrai o ID do administrador (herdado de Usuario) para devolver ao front-end
        if (setor.getAdministrador() != null) {
            dto.setAdministradorId(setor.getAdministrador().getId());
        }

        return dto;
    }
}