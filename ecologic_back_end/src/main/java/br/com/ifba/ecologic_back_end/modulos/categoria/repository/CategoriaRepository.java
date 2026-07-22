package br.com.ifba.ecologic_back_end.modulos.categoria.repository;

import br.com.ifba.ecologic_back_end.modulos.categoria.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> { // <-- Mudar de Long para UUID aqui

    Optional<Categoria> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}