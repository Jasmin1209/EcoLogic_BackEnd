package br.com.ifba.ecologic_back_end.modulos.relatorio.repository;

import br.com.ifba.ecologic_back_end.modulos.relatorio.entity.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, UUID> {
}