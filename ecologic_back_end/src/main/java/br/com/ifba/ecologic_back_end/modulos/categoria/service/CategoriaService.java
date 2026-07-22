package br.com.ifba.ecologic_back_end.modulos.categoria.service;

import br.com.ifba.ecologic_back_end.modulos.categoria.dto.CategoriaRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.categoria.dto.CategoriaResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.categoria.entity.Categoria;
import br.com.ifba.ecologic_back_end.modulos.categoria.mapper.CategoriaMapper;
import br.com.ifba.ecologic_back_end.modulos.categoria.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoriaService implements CategoriaIService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(UUID id) {
        Categoria categoria = buscarEntidadePorId(id);
        return categoriaMapper.toResponseDTO(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria buscarEntidadePorId(UUID id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id));
    }

    @Override
    @Transactional
    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {
        // Validação: Impede duplicidade de nome
        if (categoriaRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new RuntimeException("Já existe uma categoria cadastrada com o nome: " + dto.getNome());
        }

        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return categoriaMapper.toResponseDTO(categoriaSalva);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO atualizar(UUID id, CategoriaRequestDTO dto) {
        Categoria categoriaExistente = buscarEntidadePorId(id);

        // Validação: Garante que o novo nome não pertence a OUTRA categoria
        categoriaRepository.findByNomeIgnoreCase(dto.getNome())
                .ifPresent(categoriaComMesmoNome -> {
                    if (!categoriaComMesmoNome.getId().equals(id)) {
                        throw new RuntimeException("Já existe outra categoria cadastrada com este nome.");
                    }
                });

        categoriaExistente.setNome(dto.getNome());
        categoriaExistente.setDescricao(dto.getDescricao());

        Categoria categoriaAtualizada = categoriaRepository.save(categoriaExistente);
        return categoriaMapper.toResponseDTO(categoriaAtualizada);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        Categoria categoria = buscarEntidadePorId(id);

        // Trava de Segurança: Impede a exclusão de categoria que tenha produtos vinculados
        if (categoria.getProdutos() != null && !categoria.getProdutos().isEmpty()) {
            throw new RuntimeException("Não é possível excluir uma categoria que possui produtos vinculados.");
        }

        categoriaRepository.delete(categoria);
    }
}