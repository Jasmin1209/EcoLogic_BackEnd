package br.com.ifba.ecologic_back_end.modulos.produto.service;

import br.com.ifba.ecologic_back_end.modulos.categoria.entity.Categoria;
import br.com.ifba.ecologic_back_end.modulos.categoria.service.CategoriaIService;
import br.com.ifba.ecologic_back_end.modulos.produto.dto.request.ProdutoRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.produto.dto.response.ProdutoResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.produto.entity.Produto;
import br.com.ifba.ecologic_back_end.modulos.produto.mapper.ProdutoMapper;
import br.com.ifba.ecologic_back_end.modulos.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService implements ProdutoIService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;
    private final CategoriaIService categoriaService;

    @Override
    @Transactional
    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
        // Valida se o ID da categoria foi fornecido
        if (dto.getCategoriaId() == null) {
            throw new RuntimeException("É necessário informar o ID da categoria.");
        }

        // Busca e valida se a Categoria existe no banco
        Categoria categoria = categoriaService.buscarEntidadePorId(dto.getCategoriaId());

        Optional<Produto> produtoExistente = repository.findByNome(dto.getNome());

        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();
            produto.setQuantidade(produto.getQuantidade() + dto.getQuantidade());
            produto.setCategoria(categoria);
            produto = repository.save(produto);
            return mapper.toResponseDTO(produto);
        }

        Produto produto = mapper.toEntity(dto);
        produto.setCategoria(categoria); // Atribui a categoria associada

        produto = repository.save(produto);
        return mapper.toResponseDTO(produto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(UUID id) {
        Produto produto = buscarEntidadePorId(id);
        return mapper.toResponseDTO(produto);
    }

    @Override
    @Transactional(readOnly = true)
    public Produto buscarEntidadePorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
    }

    @Override
    @Transactional
    public ProdutoResponseDTO atualizar(UUID id, ProdutoRequestDTO dto) {
        Produto produto = buscarEntidadePorId(id);

        // Atualiza campos básicos (ordem dos parâmetros ajustada: dto, entidade)
        mapper.updateEntity(dto, produto);

        // Atualiza a categoria se um novo ID for informado
        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaService.buscarEntidadePorId(dto.getCategoriaId());
            produto.setCategoria(categoria);
        }

        produto = repository.save(produto);
        return mapper.toResponseDTO(produto);
    }

    @Override
    @Transactional
    public void deletar(UUID id) {
        Produto produto = buscarEntidadePorId(id);
        repository.delete(produto);
    }
}