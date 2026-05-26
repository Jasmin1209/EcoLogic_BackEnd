package br.com.ifba.ecologic_back_end.modulos.usuario.service;

import br.com.ifba.ecologic_back_end.exception.BusinessException;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioAdministradorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioDiretorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioAdministradorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioDiretorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.entity.UsuarioAdministrador;
import br.com.ifba.ecologic_back_end.modulos.usuario.entity.UsuarioDiretor;
import br.com.ifba.ecologic_back_end.modulos.usuario.mapper.UsuarioMapper;
import br.com.ifba.ecologic_back_end.modulos.usuario.repository.UsuarioAdministradorRepository;
import br.com.ifba.ecologic_back_end.modulos.usuario.repository.UsuarioDiretorRepository;
import br.com.ifba.ecologic_back_end.modulos.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementação do serviço de Usuário.
 */
@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioIService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioAdministradorRepository administradorRepository;
    private final UsuarioDiretorRepository diretorRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioAdministradorResponseDTO criarAdministrador(UsuarioAdministradorRequestDTO dto) {
        // Verifica se o email já está cadastrado
        validarEmailDuplicado(dto.getEmail());

        // Converte DTO para entidade
        UsuarioAdministrador admin = usuarioMapper.toEntity(dto);
        
        // Criptografa a senha antes de salvar
        admin.setSenha(passwordEncoder.encode(admin.getSenha()));
        
        UsuarioAdministrador adminSalvo = administradorRepository.save(admin);

        return usuarioMapper.toResponseDTO(adminSalvo);
    }

    @Override
    public UsuarioDiretorResponseDTO criarDiretor(UsuarioDiretorRequestDTO dto) {
        // Verifica se o email já está cadastrado
        validarEmailDuplicado(dto.getEmail());

        // Converte DTO para entidade
        UsuarioDiretor diretor = usuarioMapper.toEntity(dto);
        
        // Criptografa a senha antes de salvar
        diretor.setSenha(passwordEncoder.encode(diretor.getSenha()));
        
        UsuarioDiretor diretorSalvo = diretorRepository.save(diretor);

        return usuarioMapper.toResponseDTO(diretorSalvo);
    }

    /**
     * Valida se já existe um usuário com o email informado.
     * Lança BusinessException caso o email já esteja em uso.
     */
    private void validarEmailDuplicado(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new BusinessException("Já existe um usuário cadastrado com o email: " + email);
        }
    }
}