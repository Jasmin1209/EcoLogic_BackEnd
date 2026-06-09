package br.com.ifba.ecologic_back_end.modulos.estoque.repository;

import br.com.ifba.ecologic_back_end.modulos.estoque.entity.Estoque;
import br.com.ifba.ecologic_back_end.modulos.produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EstoqueRepository extends JpaRepository<Estoque, UUID> {

    Optional<Estoque> findByProduto(Produto produto);
}
