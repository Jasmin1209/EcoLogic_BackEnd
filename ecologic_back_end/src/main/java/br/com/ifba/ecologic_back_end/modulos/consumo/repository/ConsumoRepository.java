package br.com.ifba.ecologic_back_end.modulos.consumo.repository;

import br.com.ifba.ecologic_back_end.modulos.consumo.entity.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsumoRepository extends JpaRepository<Consumo, UUID> {
}
