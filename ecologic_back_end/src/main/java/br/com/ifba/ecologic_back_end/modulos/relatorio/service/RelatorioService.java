package br.com.ifba.ecologic_back_end.modulos.relatorio.service;

import br.com.ifba.ecologic_back_end.exception.BusinessException;
import br.com.ifba.ecologic_back_end.modulos.relatorio.dto.request.RelatorioRequestDto;
import br.com.ifba.ecologic_back_end.modulos.relatorio.dto.response.RelatorioResponseDto;
import br.com.ifba.ecologic_back_end.modulos.relatorio.entity.Relatorio;
import br.com.ifba.ecologic_back_end.modulos.relatorio.entity.TipoRelatorio;
import br.com.ifba.ecologic_back_end.modulos.relatorio.mapper.RelatorioMapper;
import br.com.ifba.ecologic_back_end.modulos.relatorio.repository.RelatorioRepository;
import br.com.ifba.ecologic_back_end.modulos.setor.entity.Setor;
import br.com.ifba.ecologic_back_end.modulos.setor.repository.SetorRepository;
import br.com.ifba.ecologic_back_end.modulos.consumo.repository.ConsumoRepository;
import br.com.ifba.ecologic_back_end.modulos.consumo.mapper.ConsumoMapper;
import br.com.ifba.ecologic_back_end.modulos.consumo.entity.Consumo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioService implements RelatorioIService {

    private final RelatorioRepository relatorioRepository;
    private final SetorRepository setorRepository;
    private final RelatorioMapper relatorioMapper;
    private final ConsumoRepository consumoRepository;
    private final ConsumoMapper consumoMapper;

    @Override
    @Transactional
    public RelatorioResponseDto gerarRelatorio(RelatorioRequestDto dto) {

        validarPeriodo(dto);

        Relatorio relatorio = relatorioMapper.toEntity(dto);

        // Busca o setor quando informado
        if (dto.getSetorId() != null) {
            relatorio.setSetor(buscarSetorPorId(dto.getSetorId()));
        }

        // Define a data de geração
        relatorio.setDataGeracao(LocalDateTime.now());

        // Define o título automaticamente
        relatorio.setTitulo(gerarTitulo(relatorio));

        Relatorio relatorioSalvo = relatorioRepository.save(relatorio);

        // Monta DTO básico
        RelatorioResponseDto response = relatorioMapper.toResponseDTO(relatorioSalvo);

        // Carrega consumos do período (e por setor, se informado)
        LocalDate inicio = relatorioSalvo.getPeriodoInicio().toLocalDate();
        LocalDate fim = relatorioSalvo.getPeriodoFim().toLocalDate();

        List<Consumo> consumosEntity;
        if (relatorioSalvo.getSetor() != null) {
            consumosEntity = consumoRepository.findBySetorIdAndDataRetiradaBetween(
                    relatorioSalvo.getSetor().getId(), inicio, fim
            );
        } else {
            consumosEntity = consumoRepository.findByDataRetiradaBetween(inicio, fim);
        }

        // Mapear e calcular totais
        List<br.com.ifba.ecologic_back_end.modulos.consumo.dto.response.ConsumoResponseDTO> consumoDtos =
                consumosEntity.stream()
                        .map(consumoMapper::toResponseDTO)
                        .collect(Collectors.toList());

        int totalQtd = consumosEntity.stream().mapToInt(Consumo::getQuantidade).sum();
        double totalCusto = consumosEntity.stream()
                .mapToDouble(c -> c.getQuantidade() * c.getProduto().getCustoUnitario())
                .sum();

        response.setConsumos(consumoDtos);
        response.setTotalQuantidade(totalQtd);
        response.setCustoTotal(totalCusto);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelatorioResponseDto> listarTodos() {

        return relatorioRepository.findAll()
                .stream()
                .map(relatorioMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RelatorioResponseDto buscarPorId(UUID id) {

        Relatorio relatorio = relatorioRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Relatório não encontrado."));

        RelatorioResponseDto response = relatorioMapper.toResponseDTO(relatorio);

        // Enriquecer com consumos e totais (mesma lógica usada na geração)
        LocalDate inicio = relatorio.getPeriodoInicio().toLocalDate();
        LocalDate fim = relatorio.getPeriodoFim().toLocalDate();

        List<Consumo> consumosEntity;
        if (relatorio.getSetor() != null) {
            consumosEntity = consumoRepository.findBySetorIdAndDataRetiradaBetween(
                    relatorio.getSetor().getId(), inicio, fim
            );
        } else {
            consumosEntity = consumoRepository.findByDataRetiradaBetween(inicio, fim);
        }

        List<br.com.ifba.ecologic_back_end.modulos.consumo.dto.response.ConsumoResponseDTO> consumoDtos =
                consumosEntity.stream().map(consumoMapper::toResponseDTO).collect(Collectors.toList());

        int totalQtd = consumosEntity.stream().mapToInt(Consumo::getQuantidade).sum();
        double totalCusto = consumosEntity.stream()
                .mapToDouble(c -> c.getQuantidade() * c.getProduto().getCustoUnitario())
                .sum();

        response.setConsumos(consumoDtos);
        response.setTotalQuantidade(totalQtd);
        response.setCustoTotal(totalCusto);

        return response;
    }

    @Override
    @Transactional
    public void deletar(UUID id) {

        if (!relatorioRepository.existsById(id)) {
            throw new EntityNotFoundException("Relatório não encontrado.");
        }

        relatorioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RelatorioResponseDto atualizar(UUID id, RelatorioRequestDto dto) {

        validarPeriodo(dto);

        // Busca o relatório
        Relatorio relatorio = relatorioRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Relatório não encontrado."));

        // Atualiza os campos básicos
        relatorio.setTipoRelatorio(dto.getTipoRelatorio());
        relatorio.setPeriodoInicio(dto.getPeriodoInicio());
        relatorio.setPeriodoFim(dto.getPeriodoFim());

        // Atualiza o setor
        if (dto.getSetorId() != null) {
            relatorio.setSetor(buscarSetorPorId(dto.getSetorId())
            );
        } else {
            relatorio.setSetor(null);
        }

        // Atualiza o título conforme o tipo
        relatorio.setTitulo(gerarTitulo(relatorio)
        );

        Relatorio relatorioAtualizado = relatorioRepository.save(relatorio);

        // Reutiliza método de busca para enriquecer o DTO
        return buscarPorId(relatorioAtualizado.getId());
    }

    // Valida os dados do período
    private void validarPeriodo(RelatorioRequestDto dto) {

        if (dto.getPeriodoFim().isBefore(dto.getPeriodoInicio())) {
            throw new BusinessException(
                    "A data final não pode ser anterior à data inicial.");
        }

        if (dto.getTipoRelatorio() == TipoRelatorio.POR_SETOR
                && dto.getSetorId() == null) {

            throw new BusinessException(
                    "Relatórios por setor exigem um setor.");
        }
    }

    // Busca um setor pelo id
    private Setor buscarSetorPorId(Long setorId) {

        return setorRepository.findById(setorId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Setor não encontrado."));
    }

    // Gera um título para o relatório
    private String gerarTitulo(Relatorio relatorio) {

        if (relatorio.getTipoRelatorio() == TipoRelatorio.GERAL) {
            return "Relatório Geral";
        }

        return "Relatório - " + relatorio.getSetor().getNome();
    }
}