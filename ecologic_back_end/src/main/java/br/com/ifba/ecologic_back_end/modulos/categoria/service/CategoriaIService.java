package br.com.ifba.ecologic_back_end.modulos.categoria.service;

import br.com.ifba.ecologic_back_end.modulos.categoria.dto.CategoriaRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.categoria.dto.CategoriaResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.categoria.entity.Categoria;

import java.util.List;
import java.util.UUID;

public interface CategoriaIService {

    List<CategoriaResponseDTO> listarTodas();

    CategoriaResponseDTO buscarPorId(UUID id);

    // Método crucial para o ProdutoService conseguir buscar a Entidade Categoria no banco
    Categoria buscarEntidadePorId(UUID id);

    CategoriaResponseDTO salvar(CategoriaRequestDTO dto);

    CategoriaResponseDTO atualizar(UUID id, CategoriaRequestDTO dto);

    void excluir(UUID id);
}