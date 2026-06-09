package br.com.ifba.ecologic_back_end.modulos.setor.repository;

import br.com.ifba.ecologic_back_end.modulos.setor.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {
    // Busca um setor pelo nome exato para podermos validar duplicidade
    Optional<Setor> findByNome(String nome);
}