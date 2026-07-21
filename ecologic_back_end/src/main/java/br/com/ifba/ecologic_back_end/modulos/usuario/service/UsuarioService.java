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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public UsuarioAdministradorResponseDTO criarAdministrador(UsuarioAdministradorRequestDTO dto) {
        validarEmailDuplicado(dto.getEmail());
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuarioSalvo);
    }

    @Override
    @Transactional
    public UsuarioDiretorResponseDTO criarDiretor(UsuarioDiretorRequestDTO dto) {
        validarEmailDuplicado(dto.getEmail());
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTODiretor(usuarioSalvo);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO getUsuario(String nome) {
        Usuario usuario = usuarioRepository.findByNome(nome)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com nome: " + nome));
        return usuarioMapper.toGenericResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO getUsuarioPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com id: " + id));
        return usuarioMapper.toGenericResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toGenericResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioAdministradorResponseDTO> listarAdministradores() {
        return usuarioRepository.findByTipo(TipoUsuario.ADMINISTRADOR)
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDiretorResponseDTO> listarDiretores() {
        return usuarioRepository.findByTipo(TipoUsuario.DIRETOR)
                .stream()
                .map(usuarioMapper::toResponseDTODiretor)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioResponseDTO atualizarUsuario(UUID id, UsuarioUpdateRequestDTO dto) {
        // Busca o usuário pelo ID para garantir que o update funciona mesmo se o nome mudar
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com id: " + id));

        // Atualiza o nome se foi informado e não está vazio
        if (dto.getNome() != null && !dto.getNome().isBlank()) {
            usuario.setNome(dto.getNome());
        }

        // Atualiza o email se foi informado — valida duplicidade ignorando o próprio usuário
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            validarEmailDuplicadoParaEdicao(dto.getEmail(), id);
            usuario.setEmail(dto.getEmail());
        }

        // Atualiza a senha apenas se uma nova senha foi fornecida
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        // Atualiza o campo específico do tipo (cargo para ADMINISTRADOR, titulacao para DIRETOR)
        if (dto.getAtributoEspecifico() != null && !dto.getAtributoEspecifico().isBlank()) {
            if (usuario.getTipo() == TipoUsuario.ADMINISTRADOR) {
                usuario.setCargo(dto.getAtributoEspecifico());
            } else if (usuario.getTipo() == TipoUsuario.DIRETOR) {
                usuario.setTitulacao(dto.getAtributoEspecifico());
            }
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toGenericResponseDTO(usuarioAtualizado);
    }

    @Override
    @Transactional
    public void deletarUsuario(UUID id) {
        // Busca pelo ID para garantir que o registro existe antes de deletar
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com id: " + id));
        usuarioRepository.delete(usuario);
    }

    /**
     * Valida se já existe um usuário com o email informado (para criação).
     */
    private void validarEmailDuplicado(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new BusinessException("Já existe um usuário cadastrado com o email: " + email);
        }
    }

    /**
     * Valida duplicidade de email na edição, ignorando o próprio usuário que está sendo editado.
     */
    private void validarEmailDuplicadoParaEdicao(String email, UUID idAtual) {
        usuarioRepository.findByEmail(email).ifPresent(existente -> {
            if (!existente.getId().equals(idAtual)) {
                throw new BusinessException("Já existe outro usuário cadastrado com o email: " + email);
            }
        });
    }
}