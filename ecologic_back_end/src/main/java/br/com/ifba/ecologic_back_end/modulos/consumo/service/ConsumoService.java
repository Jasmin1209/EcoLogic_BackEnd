package br.com.ifba.ecologic_back_end.modulos.consumo.service;

import br.com.ifba.ecologic_back_end.modulos.consumo.dto.request.ConsumoRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.consumo.dto.response.ConsumoResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.consumo.entity.Consumo;
import br.com.ifba.ecologic_back_end.modulos.consumo.mapper.ConsumoMapper;
import br.com.ifba.ecologic_back_end.modulos.consumo.repository.ConsumoRepository;
import br.com.ifba.ecologic_back_end.modulos.produto.entity.Produto;
import br.com.ifba.ecologic_back_end.modulos.produto.repository.ProdutoRepository;
import br.com.ifba.ecologic_back_end.modulos.setor.entity.Setor;
import br.com.ifba.ecologic_back_end.modulos.setor.repository.SetorRepository;
import br.com.ifba.ecologic_back_end.modulos.estoque.entity.Estoque;
import br.com.ifba.ecologic_back_end.modulos.estoque.repository.EstoqueRepository;
import br.com.ifba.ecologic_back_end.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsumoService implements ConsumoIService {

    private final ConsumoRepository repository;

    private final ProdutoRepository produtoRepository;

    // Estoque repository: mantido como fallback para projetos que ainda usam a entidade Estoque
    private final EstoqueRepository estoqueRepository;

    private final SetorRepository setorRepository;

    private final ConsumoMapper mapper;

    @Override
    public ConsumoResponseDTO salvar(
            ConsumoRequestDTO dto
    ) {

        Produto produto = produtoRepository
                .findById(dto.getProdutoId())
                .orElseThrow(() ->
                        new BusinessException("Produto não encontrado"));

        // Preferir usar quantidade no próprio produto quando disponível
        Integer produtoQuantidade = produto.getQuantidade();

        if (produtoQuantidade != null) {
            if (produtoQuantidade >= dto.getQuantidade()) {
                produto.setQuantidade(produtoQuantidade - dto.getQuantidade());
                produtoRepository.save(produto);
            } else {
                // Tentar fallback para entidade Estoque (legado)
                Estoque estoque = estoqueRepository
                        .findByProduto(produto)
                        .orElseThrow(() -> new BusinessException("Estoque insuficiente"));

                if (estoque.getQuantidadeAtual() < dto.getQuantidade()) {
                    throw new BusinessException("Estoque insuficiente");
                }

                estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() - dto.getQuantidade());
                estoqueRepository.save(estoque);
            }
        } else {
            // produto.quantidade indefinida: usar Estoque como fonte única
            Estoque estoque = estoqueRepository
                    .findByProduto(produto)
                    .orElseThrow(() -> new BusinessException("Estoque insuficiente"));

            if (estoque.getQuantidadeAtual() < dto.getQuantidade()) {
                throw new BusinessException("Estoque insuficiente");
            }

            estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() - dto.getQuantidade());
            estoqueRepository.save(estoque);
        }

        Consumo consumo = new Consumo();

        consumo.setQuantidade(dto.getQuantidade());

        consumo.setJustificativa(
                dto.getJustificativa()
        );

        consumo.setDataRetirada(LocalDate.now());

        consumo.setProduto(produto);

        // Vincula o setor que está realizando o consumo
        if (dto.getSetorId() == null) {
            throw new BusinessException("Setor não informado");
        }

        Setor setor = setorRepository
                .findById(dto.getSetorId())
                .orElseThrow(() -> new BusinessException("Setor não encontrado"));

        consumo.setSetor(setor);

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
