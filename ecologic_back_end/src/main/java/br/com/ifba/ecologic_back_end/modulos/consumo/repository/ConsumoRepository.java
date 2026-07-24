package br.com.ifba.ecologic_back_end.modulos.consumo.repository;

import br.com.ifba.ecologic_back_end.modulos.consumo.entity.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ConsumoRepository extends JpaRepository<Consumo, UUID> {

	// Busca consumos dentro de um intervalo de datas
	List<Consumo> findByDataRetiradaBetween(LocalDate inicio, LocalDate fim);

	// Busca consumos de um setor específico dentro do intervalo
	List<Consumo> findBySetorIdAndDataRetiradaBetween(Long setorId, LocalDate inicio, LocalDate fim);

	// Retorna as quantidades de todos os pedidos de um produto (para calcular média e mediana)
	@Query("SELECT c.quantidade FROM Consumo c WHERE c.produto.id = :produtoId")
	List<Integer> findQuantidadesByProdutoId(@Param("produtoId") UUID produtoId);

	// Retorna o nome do setor de todos os consumos (para calcular a Moda)
	@Query("SELECT c.setor.nome FROM Consumo c WHERE c.setor IS NOT NULL")
	List<String> findAllNomesSetor();

	// Retorna o gasto total de cada setor, ordenado do menor para o maior (para calcular quartis)
	@Query("SELECT SUM(c.quantidade * c.produto.custoUnitario) FROM Consumo c WHERE c.setor IS NOT NULL GROUP BY c.setor.id ORDER BY SUM(c.quantidade * c.produto.custoUnitario) ASC")
	List<Double> findGastoTotalPorSetorOrdenado();

	// Retorna o gasto total de um setor específico
	@Query("SELECT SUM(c.quantidade * c.produto.custoUnitario) FROM Consumo c WHERE c.setor.id = :setorId")
	Double findGastoTotalBySetorId(@Param("setorId") Long setorId);
}
