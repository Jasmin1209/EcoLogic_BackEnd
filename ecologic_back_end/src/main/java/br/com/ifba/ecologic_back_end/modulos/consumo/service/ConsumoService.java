package br.com.ifba.ecologic_back_end.modulos.consumo.service;

import br.com.ifba.ecologic_back_end.modulos.consumo.dto.request.ConsumoRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.consumo.dto.response.ConsumoResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.consumo.entity.Consumo;
import br.com.ifba.ecologic_back_end.modulos.consumo.mapper.ConsumoMapper;
import br.com.ifba.ecologic_back_end.modulos.consumo.repository.ConsumoRepository;
import br.com.ifba.ecologic_back_end.modulos.estoque.entity.Estoque;
import br.com.ifba.ecologic_back_end.modulos.estoque.repository.EstoqueRepository;
import br.com.ifba.ecologic_back_end.modulos.produto.entity.Produto;
import br.com.ifba.ecologic_back_end.modulos.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ConsumoService implements ConsumoIService {

    private final ConsumoRepository repository;

    private final ProdutoRepository produtoRepository;

    private final EstoqueRepository estoqueRepository;

    private final ConsumoMapper mapper;

    @Override
    public ConsumoResponseDTO salvar(
            ConsumoRequestDTO dto
    ) {

        Produto produto = produtoRepository
                .findById(dto.getProdutoId())
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        Estoque estoque = estoqueRepository
                .findByProduto(produto)
                .orElseThrow(() ->
                        new RuntimeException("Estoque não encontrado"));

        if (estoque.getQuantidadeAtual()
                < dto.getQuantidade()) {

            throw new RuntimeException(
                    "Estoque insuficiente"
            );
        }

        estoque.setQuantidadeAtual(
                estoque.getQuantidadeAtual()
                        - dto.getQuantidade()
        );

        estoqueRepository.save(estoque);

        Consumo consumo = new Consumo();

        consumo.setQuantidade(dto.getQuantidade());

        consumo.setJustificativa(
                dto.getJustificativa()
        );

        consumo.setDataRetirada(LocalDate.now());

        consumo.setProduto(produto);

        return mapper.toResponseDTO(
                repository.save(consumo)
        );
    }

    @Override
    public List<ConsumoResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }
}
