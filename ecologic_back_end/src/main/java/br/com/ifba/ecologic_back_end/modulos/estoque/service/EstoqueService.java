package br.com.ifba.ecologic_back_end.modulos.estoque.service;

import br.com.ifba.ecologic_back_end.modulos.estoque.dto.request.EstoqueRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.estoque.dto.response.EstoqueResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.estoque.entity.Estoque;
import br.com.ifba.ecologic_back_end.modulos.estoque.mapper.EstoqueMapper;
import br.com.ifba.ecologic_back_end.modulos.estoque.repository.EstoqueRepository;
import br.com.ifba.ecologic_back_end.modulos.produto.entity.Produto;
import br.com.ifba.ecologic_back_end.modulos.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueService implements EstoqueIService {

    private final EstoqueRepository repository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueMapper mapper;

    @Override
    public EstoqueResponseDTO salvar(
            EstoqueRequestDTO dto
    ) {

        Produto produto = produtoRepository
                .findById(dto.getProdutoId())
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        Estoque estoque = new Estoque();

        estoque.setQuantidadeAtual(dto.getQuantidadeAtual());
        estoque.setQuantidadeMinima(dto.getQuantidadeMinima());
        estoque.setProduto(produto);

        return mapper.toResponseDTO(
                repository.save(estoque)
        );
    }

    @Override
    public List<EstoqueResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }
}
