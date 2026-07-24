package br.com.ifba.ecologic_back_end.modulos.estatistica.service;

import br.com.ifba.ecologic_back_end.modulos.consumo.repository.ConsumoRepository;
import br.com.ifba.ecologic_back_end.modulos.estatistica.dto.response.EstatisticaProdutoDTO;
import br.com.ifba.ecologic_back_end.modulos.estatistica.dto.response.ModaSetorDTO;
import br.com.ifba.ecologic_back_end.modulos.estatistica.dto.response.QuartilSetorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EstatisticaService {

    @Autowired
    private ConsumoRepository consumoRepository;

    // -------------------------------------------------------
    // Calcula a Média e a Mediana das quantidades de um produto
    // -------------------------------------------------------
    public EstatisticaProdutoDTO calcularEstatisticasProduto(UUID produtoId) {
        List<Integer> quantidades = consumoRepository.findQuantidadesByProdutoId(produtoId);

        if (quantidades == null || quantidades.isEmpty()) {
            return new EstatisticaProdutoDTO(0.0, 0.0);
        }

        // Calcular Média
        double soma = 0;
        for (Integer qtd : quantidades) {
            soma += qtd;
        }
        double media = soma / quantidades.size();

        // Calcular Mediana
        Collections.sort(quantidades);
        double mediana;
        int size = quantidades.size();
        if (size % 2 != 0) {
            // Tamanho ímpar: pega o elemento central
            mediana = quantidades.get(size / 2);
        } else {
            // Tamanho par: média dos dois elementos centrais
            int meio1 = quantidades.get((size / 2) - 1);
            int meio2 = quantidades.get(size / 2);
            mediana = (meio1 + meio2) / 2.0;
        }

        return new EstatisticaProdutoDTO(media, mediana);
    }

    // -------------------------------------------------------
    // Calcula a Moda dos Setores (setor que mais fez pedidos)
    // -------------------------------------------------------
    public ModaSetorDTO calcularModaSetor() {
        List<String> nomes = consumoRepository.findAllNomesSetor();

        if (nomes == null || nomes.isEmpty()) {
            return new ModaSetorDTO("Nenhum pedido registrado", 0);
        }

        // Montar o mapa de frequências: Setor -> quantidade de vezes que aparece
        HashMap<String, Integer> frequencias = new HashMap<>();
        for (String setor : nomes) {
            if (frequencias.containsKey(setor)) {
                frequencias.put(setor, frequencias.get(setor) + 1);
            } else {
                frequencias.put(setor, 1);
            }
        }

        // Encontrar o setor com a maior frequência (Moda)
        String setorModa = null;
        int maiorFrequencia = 0;
        for (Map.Entry<String, Integer> entrada : frequencias.entrySet()) {
            if (entrada.getValue() > maiorFrequencia) {
                maiorFrequencia = entrada.getValue();
                setorModa = entrada.getKey();
            }
        }

        return new ModaSetorDTO(setorModa, maiorFrequencia);
    }

    // -------------------------------------------------------
    // Calcula o Quartil do gasto de um setor (Termômetro)
    // -------------------------------------------------------
    public QuartilSetorDTO calcularQuartilSetor(Long setorId) {
        // 1. Buscar a lista de gastos totais de todos os setores, já ordenada (ASC)
        List<Double> gastosOrdenados = consumoRepository.findGastoTotalPorSetorOrdenado();

        // 2. Buscar o gasto total do setor analisado
        Double gastoDoSetor = consumoRepository.findGastoTotalBySetorId(setorId);

        if (gastosOrdenados == null || gastosOrdenados.isEmpty() || gastoDoSetor == null) {
            return new QuartilSetorDTO(0.0, "SEM DADOS", 0.0, 0.0);
        }

        int tamanho = gastosOrdenados.size();

        // 3. Calcular Q1 (índice = 25/100 * tamanho)
        int indiceQ1 = (int) (0.25 * tamanho);
        double q1 = gastosOrdenados.get(indiceQ1);

        // 4. Calcular Q3 (índice = 75/100 * tamanho)
        int indiceQ3 = (int) (0.75 * tamanho);
        // Garantir que o índice não ultrapasse o limite da lista
        if (indiceQ3 >= tamanho) {
            indiceQ3 = tamanho - 1;
        }
        double q3 = gastosOrdenados.get(indiceQ3);

        // 5. Classificar o setor
        String classificacao;
        if (gastoDoSetor <= q1) {
            classificacao = "BAIXO";
        } else if (gastoDoSetor <= q3) {
            classificacao = "NORMAL";
        } else {
            classificacao = "ALTO";
        }

        return new QuartilSetorDTO(gastoDoSetor, classificacao, q1, q3);
    }
}
