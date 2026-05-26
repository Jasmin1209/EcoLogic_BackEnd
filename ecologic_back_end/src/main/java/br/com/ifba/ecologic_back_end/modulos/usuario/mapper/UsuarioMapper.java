package br.com.ifba.ecologic_back_end.modulos.usuario.mapper;

import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioAdministradorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioDiretorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioAdministradorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioDiretorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.entity.UsuarioAdministrador;
import br.com.ifba.ecologic_back_end.modulos.usuario.entity.UsuarioDiretor;
import org.springframework.stereotype.Component;

/**
 * Componente responsável pela conversão entre Entidades e DTOs do módulo Usuário.
 * Realiza mapeamento manual para manter controle total sobre as conversões.
 */
@Component
public class UsuarioMapper {

    // ========================
    // REQUEST DTO → ENTITY
    // ========================

    /**
     * Converte UsuarioAdministradorRequestDTO para a entidade UsuarioAdministrador.
     */
    public UsuarioAdministrador toEntity(UsuarioAdministradorRequestDTO dto) {
        UsuarioAdministrador admin = new UsuarioAdministrador();
        admin.setEmail(dto.getEmail());
        admin.setSenha(dto.getSenha());
        admin.setCargo(dto.getCargo());
        return admin;
    }

    /**
     * Converte UsuarioDiretorRequestDTO para a entidade UsuarioDiretor.
     */
    public UsuarioDiretor toEntity(UsuarioDiretorRequestDTO dto) {
        UsuarioDiretor diretor = new UsuarioDiretor();
        diretor.setEmail(dto.getEmail());
        diretor.setSenha(dto.getSenha());
        diretor.setTitulacao(dto.getTitulacao());
        return diretor;
    }

    // ========================
    // ENTITY → RESPONSE DTO
    // ========================

    /**
     * Converte a entidade UsuarioAdministrador para UsuarioAdministradorResponseDTO.
     */
    public UsuarioAdministradorResponseDTO toResponseDTO(UsuarioAdministrador admin) {
        UsuarioAdministradorResponseDTO dto = new UsuarioAdministradorResponseDTO();
        dto.setId(admin.getId());
        dto.setEmail(admin.getEmail());
        dto.setCargo(admin.getCargo());
        dto.setDataCriacao(admin.getDataCriacao());
        return dto;
    }

    /**
     * Converte a entidade UsuarioDiretor para UsuarioDiretorResponseDTO.
     */
    public UsuarioDiretorResponseDTO toResponseDTO(UsuarioDiretor diretor) {
        UsuarioDiretorResponseDTO dto = new UsuarioDiretorResponseDTO();
        dto.setId(diretor.getId());
        dto.setEmail(diretor.getEmail());
        dto.setTitulacao(diretor.getTitulacao());
        dto.setDataCriacao(diretor.getDataCriacao());
        return dto;
    }
}