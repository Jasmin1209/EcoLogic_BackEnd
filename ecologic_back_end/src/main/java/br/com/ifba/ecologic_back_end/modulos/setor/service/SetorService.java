package br.com.ifba.ecologic_back_end.modulos.setor.service;

import br.com.ifba.ecologic_back_end.modulos.setor.dto.SetorRequestDto;
import br.com.ifba.ecologic_back_end.modulos.setor.dto.SetorResponseDto;
import br.com.ifba.ecologic_back_end.modulos.setor.entity.Setor;
import br.com.ifba.ecologic_back_end.modulos.setor.mapper.SetorMapper;
import br.com.ifba.ecologic_back_end.modulos.setor.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;
    private final SetorMapper setorMapper; // Injeta o novo componente de conversão

    @Transactional
    public SetorResponseDto criar(SetorRequestDto request) {
        if (setorRepository.findByNome(request.getNome()).isPresent()) {
            throw new RuntimeException("Já existe um setor cadastrado com este nome.");
        }

        // Usa o Mapper isolado para converter
        Setor setor = setorMapper.toEntity(request);
        Setor setorSalvo = setorRepository.save(setor);

        return setorMapper.toResponseDto(setorSalvo);
    }

    @Transactional(readOnly = true)
    public List<SetorResponseDto> buscarTodos() {
        List<Setor> setores = setorRepository.findAll();

        // Converte cada entidade da lista usando o método do Mapper externo
        return setores.stream()
                .map(setorMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SetorResponseDto buscarPorId(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado com o ID: " + id));

        return setorMapper.toResponseDto(setor);
    }

    @Transactional
    public SetorResponseDto atualizar(Long id, SetorRequestDto request) {
        Setor setorExistente = setorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado com o ID: " + id));

        setorRepository.findByNome(request.getNome())
                .ifPresent(setorComMesmoNome -> {
                    if (!setorComMesmoNome.getId().equals(id)) {
                        throw new RuntimeException("O nome deste setor já está em uso por outro registro.");
                    }
                });

        setorExistente.setNome(request.getNome());
        setorExistente.setDescricao(request.getDescricao());

        Setor setorAtualizado = setorRepository.save(setorExistente);
        return setorMapper.toResponseDto(setorAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado com o ID: " + id));

        setorRepository.delete(setor);
    }
}