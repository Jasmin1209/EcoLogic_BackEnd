package br.com.ifba.ecologic_back_end.modulos.usuario.mapper;

import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioAdministradorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioDiretorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioAdministradorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioDiretorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.enums.TipoUsuario;
import br.com.ifba.ecologic_back_end.modulos.usuario.entity.Usuario;
import org.springframework.stereotype.Component;

/**
 * Componente responsável pela conversão entre Entidades e DTOs do módulo Usuário.
 */
@Component
public class UsuarioMapper {

    /**
     * Converte UsuarioAdministradorRequestDTO para a entidade Usuario.
     */
    public Usuario toEntity(UsuarioAdministradorRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setTipo(TipoUsuario.ADMINISTRADOR);
        usuario.setCargo(dto.getCargo());
        return usuario;
    }

    /**
     * Converte UsuarioDiretorRequestDTO para a entidade Usuario.
     */
    public Usuario toEntity(UsuarioDiretorRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setTipo(TipoUsuario.DIRETOR);
        usuario.setTitulacao(dto.getTitulacao());
        return usuario;
    }

    /**
     * Converte a entidade Usuario para UsuarioAdministradorResponseDTO.
     */
    public UsuarioAdministradorResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioAdministradorResponseDTO dto = new UsuarioAdministradorResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setCargo(usuario.getCargo());
        dto.setDataCriacao(usuario.getDataCriacao());
        return dto;
    }

    /**
     * Converte a entidade Usuario para UsuarioDiretorResponseDTO.
     */
    public UsuarioDiretorResponseDTO toResponseDTODiretor(Usuario usuario) {
        UsuarioDiretorResponseDTO dto = new UsuarioDiretorResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTitulacao(usuario.getTitulacao());
        dto.setDataCriacao(usuario.getDataCriacao());
        return dto;
    }

    /**
     * Converte qualquer Usuario para UsuarioResponseDTO genérico.
     */
    public UsuarioResponseDTO toGenericResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setDataCriacao(usuario.getDataCriacao());
        dto.setTipo(usuario.getTipo().name());
        
        if (usuario.getTipo() == TipoUsuario.ADMINISTRADOR) {
            dto.setAtributoEspecifico(usuario.getCargo());
        } else if (usuario.getTipo() == TipoUsuario.DIRETOR) {
            dto.setAtributoEspecifico(usuario.getTitulacao());
        }

        return dto;
    }
}