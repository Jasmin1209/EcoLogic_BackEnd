package br.com.ifba.ecologic_back_end.modulos.usuario.service;

import br.com.ifba.ecologic_back_end.exception.BusinessException;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioAdministradorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioDiretorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioUpdateRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioAdministradorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioDiretorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.enums.TipoUsuario;
import br.com.ifba.ecologic_back_end.modulos.usuario.entity.Usuario;
import br.com.ifba.ecologic_back_end.modulos.usuario.mapper.UsuarioMapper;
import br.com.ifba.ecologic_back_end.modulos.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementação do serviço de Usuário.
 */
@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioIService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioAdministradorResponseDTO criarAdministrador(UsuarioAdministradorRequestDTO dto) {
        validarEmailDuplicado(dto.getEmail());
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuarioSalvo);
    }

    @Override
    public UsuarioDiretorResponseDTO criarDiretor(UsuarioDiretorRequestDTO dto) {
        validarEmailDuplicado(dto.getEmail());
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTODiretor(usuarioSalvo);
    }

    @Override
    public UsuarioResponseDTO getUsuario(String nome) {
        Usuario usuario = usuarioRepository.findByNome(nome)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com nome: " + nome));
        return usuarioMapper.toGenericResponseDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO getUsuarioPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com id: " + id));
        return usuarioMapper.toGenericResponseDTO(usuario);
    }

    @Override
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toGenericResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioAdministradorResponseDTO> listarAdministradores() {
        return usuarioRepository.findByTipo(TipoUsuario.ADMINISTRADOR)
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDiretorResponseDTO> listarDiretores() {
        return usuarioRepository.findByTipo(TipoUsuario.DIRETOR)
                .stream()
                .map(usuarioMapper::toResponseDTODiretor)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO atualizarUsuario(String nome, UsuarioUpdateRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByNome(nome)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com nome: " + nome));

        if (usuario.getTipo() == TipoUsuario.ADMINISTRADOR) {
            usuario.setCargo(dto.getAtributoEspecifico());
        } else if (usuario.getTipo() == TipoUsuario.DIRETOR) {
            usuario.setTitulacao(dto.getAtributoEspecifico());
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toGenericResponseDTO(usuarioAtualizado);
    }

    @Override
    public void deletarUsuario(String nome) {
        Usuario usuario = usuarioRepository.findByNome(nome)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com nome: " + nome));
        usuarioRepository.delete(usuario);
    }

    /**
     * Valida se já existe um usuário com o email informado.
     */
    private void validarEmailDuplicado(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new BusinessException("Já existe um usuário cadastrado com o email: " + email);
        }
    }
}
