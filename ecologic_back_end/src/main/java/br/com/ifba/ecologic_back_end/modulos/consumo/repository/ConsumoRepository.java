package br.com.ifba.ecologic_back_end.modulos.consumo.repository;

import br.com.ifba.ecologic_back_end.modulos.consumo.entity.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ConsumoRepository extends JpaRepository<Consumo, UUID> {

	// Busca consumos dentro de um intervalo de datas
	List<Consumo> findByDataRetiradaBetween(LocalDate inicio, LocalDate fim);

	// Busca consumos de um setor específico dentro do intervalo
	List<Consumo> findBySetorIdAndDataRetiradaBetween(Long setorId, LocalDate inicio, LocalDate fim);
}
