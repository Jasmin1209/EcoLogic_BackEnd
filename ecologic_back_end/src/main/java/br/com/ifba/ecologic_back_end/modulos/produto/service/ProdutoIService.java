package br.com.ifba.ecologic_back_end.modulos.produto.service;

import br.com.ifba.ecologic_back_end.modulos.produto.dto.request.ProdutoRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.produto.dto.response.ProdutoResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.produto.entity.Produto;

import java.util.List;
import java.util.UUID;

public interface ProdutoIService {

    ProdutoResponseDTO salvar(ProdutoRequestDTO dto);

    List<ProdutoResponseDTO> listar();

    ProdutoResponseDTO buscarPorId(UUID id);

    Produto buscarEntidadePorId(UUID id);

    ProdutoResponseDTO atualizar(UUID id, ProdutoRequestDTO dto);

    void deletar(UUID id);
}