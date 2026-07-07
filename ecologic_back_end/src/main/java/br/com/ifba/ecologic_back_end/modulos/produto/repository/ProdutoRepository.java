package br.com.ifba.ecologic_back_end.modulos.produto.repository;

import br.com.ifba.ecologic_back_end.modulos.produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    Optional<Produto> findByNome(String nome);
}
