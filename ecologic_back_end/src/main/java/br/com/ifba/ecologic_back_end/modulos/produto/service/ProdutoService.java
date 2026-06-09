package br.com.ifba.ecologic_back_end.modulos.produto.service;

import br.com.ifba.ecologic_back_end.modulos.produto.dto.request.ProdutoRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.produto.dto.response.ProdutoResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.produto.entity.Produto;
import br.com.ifba.ecologic_back_end.modulos.produto.mapper.ProdutoMapper;
import br.com.ifba.ecologic_back_end.modulos.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService implements ProdutoIService{

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    @Override
    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {

        Produto produto = mapper.toEntity(dto);

        produto = repository.save(produto);

        return mapper.toResponseDTO(produto);
    }

    @Override
    public List<ProdutoResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProdutoResponseDTO buscarPorId(UUID id) {

        Produto produto = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        return mapper.toResponseDTO(produto);
    }

    @Override
    public ProdutoResponseDTO atualizar(
            UUID id,
            ProdutoRequestDTO dto
    ) {

        Produto produto = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        mapper.updateEntity(produto, dto);

        produto = repository.save(produto);

        return mapper.toResponseDTO(produto);
    }

    @Override
    public void deletar(UUID id) {

        Produto produto = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        repository.delete(produto);
    }
}
