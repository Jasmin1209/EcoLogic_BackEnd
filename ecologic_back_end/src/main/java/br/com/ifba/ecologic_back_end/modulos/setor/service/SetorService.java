package br.com.ifba.ecologic_back_end.modulos.setor.service;

import br.com.ifba.ecologic_back_end.exception.BusinessException;
import br.com.ifba.ecologic_back_end.modulos.setor.dto.request.SetorRequestDto;
import br.com.ifba.ecologic_back_end.modulos.setor.dto.response.SetorResponseDto;
import br.com.ifba.ecologic_back_end.modulos.setor.entity.Setor;
import br.com.ifba.ecologic_back_end.modulos.setor.mapper.SetorMapper;
import br.com.ifba.ecologic_back_end.modulos.setor.repository.SetorRepository;
import br.com.ifba.ecologic_back_end.modulos.usuario.enums.TipoUsuario;
import br.com.ifba.ecologic_back_end.modulos.usuario.entity.Usuario;
import br.com.ifba.ecologic_back_end.modulos.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;
    private final SetorMapper setorMapper;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public SetorResponseDto criar(SetorRequestDto request) {
        if (setorRepository.findByNome(request.getNome()).isPresent()) {
            throw new BusinessException("Já existe um setor cadastrado com este nome.");
        }

        Setor setor = setorMapper.toEntity(request);
        
        // Valida e atribui o administrador se foi informado
        if (request.getAdministradorId() != null) {
            Usuario administrador = usuarioRepository.findById(request.getAdministradorId())
                    .orElseThrow(() -> new BusinessException("Usuário não encontrado com o ID: " + request.getAdministradorId()));
            
            if (administrador.getTipo() != TipoUsuario.ADMINISTRADOR) {
                throw new BusinessException("O usuário informado não é um administrador.");
            }
            
            setor.setAdministrador(administrador);
        }
        
        Setor setorSalvo = setorRepository.save(setor);

        return setorMapper.toResponseDto(setorSalvo);
    }

    @Transactional(readOnly = true)
    public List<SetorResponseDto> buscarTodos() {
        List<Setor> setores = setorRepository.findAll();
        return setores.stream()
                .map(setorMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SetorResponseDto buscarPorId(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Setor não encontrado com o ID: " + id));

        return setorMapper.toResponseDto(setor);
    }

    @Transactional
    public SetorResponseDto atualizar(Long id, SetorRequestDto request) {
        Setor setorExistente = setorRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Setor não encontrado com o ID: " + id));

        setorRepository.findByNome(request.getNome())
                .ifPresent(setorComMesmoNome -> {
                    if (!setorComMesmoNome.getId().equals(id)) {
                        throw new BusinessException("O nome deste setor já está em uso por outro registro.");
                    }
                });

        // Atualiza os dados básicos
        setorExistente.setNome(request.getNome());
        setorExistente.setDescricao(request.getDescricao());

        // Atualiza o vínculo do administrador caso tenha sido alterado
        if (request.getAdministradorId() != null) {
            Usuario administrador = usuarioRepository.findById(request.getAdministradorId())
                    .orElseThrow(() -> new BusinessException("Usuário não encontrado com o ID: " + request.getAdministradorId()));
            
            if (administrador.getTipo() != TipoUsuario.ADMINISTRADOR) {
                throw new BusinessException("O usuário informado não é um administrador.");
            }
            
            setorExistente.setAdministrador(administrador);
        } else {
            setorExistente.setAdministrador(null);
        }

        Setor setorAtualizado = setorRepository.save(setorExistente);
        return setorMapper.toResponseDto(setorAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Setor não encontrado com o ID: " + id));

        setorRepository.delete(setor);
    }
}