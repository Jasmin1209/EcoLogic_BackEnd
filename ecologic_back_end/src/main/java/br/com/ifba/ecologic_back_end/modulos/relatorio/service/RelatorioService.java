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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.io.ByteArrayOutputStream;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

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

    @Override
    @Transactional(readOnly = true)
    public byte[] exportarPdf(UUID id) {
        // Obtém os dados estruturados do relatório
        RelatorioResponseDto relatorio = buscarPorId(id);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Configuração de fontes
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);

            // Título do Relatório
            Paragraph titulo = new Paragraph(relatorio.getTitulo(), tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(15);
            document.add(titulo);

            // Informações gerais
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            Paragraph info = new Paragraph();
            info.setFont(regularFont);
            info.add(new Phrase("Tipo: ", boldFont));
            info.add(relatorio.getTipoRelatorio().toString() + "\n");
            info.add(new Phrase("Data de Geração: ", boldFont));
            info.add(relatorio.getDataGeracao().format(formatter) + "\n");
            info.add(new Phrase("Período: ", boldFont));
            info.add(relatorio.getPeriodoInicio().format(dateFormatter) + " até " + relatorio.getPeriodoFim().format(dateFormatter) + "\n");
            if (relatorio.getNomeSetor() != null) {
                info.add(new Phrase("Setor: ", boldFont));
                info.add(relatorio.getNomeSetor() + "\n");
            }
            if (relatorio.getAdministradorNome() != null) {
                info.add(new Phrase("Administrador: ", boldFont));
                info.add(relatorio.getAdministradorNome() + "\n");
            }
            info.setSpacingAfter(20);
            document.add(info);

            // Tabela de consumos
            Paragraph sectionTitle = new Paragraph("Consumos Realizados", sectionFont);
            sectionTitle.setSpacingAfter(10);
            document.add(sectionTitle);

            if (relatorio.getConsumos() == null || relatorio.getConsumos().isEmpty()) {
                Paragraph noConsumo = new Paragraph("Nenhum consumo registrado neste período.", regularFont);
                noConsumo.setSpacingAfter(15);
                document.add(noConsumo);
            } else {
                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{30f, 15f, 20f, 35f});

                // Headers da tabela
                PdfPCell c1 = new PdfPCell(new Phrase("Produto", boldFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);

                c1 = new PdfPCell(new Phrase("Qtd", boldFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);

                c1 = new PdfPCell(new Phrase("Data Retirada", boldFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);

                c1 = new PdfPCell(new Phrase("Justificativa", boldFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);

                // Linhas da tabela de consumos
                for (br.com.ifba.ecologic_back_end.modulos.consumo.dto.response.ConsumoResponseDTO c : relatorio.getConsumos()) {
                    table.addCell(new Phrase(c.getNomeProduto(), regularFont));

                    PdfPCell cellQtd = new PdfPCell(new Phrase(String.valueOf(c.getQuantidade()), regularFont));
                    cellQtd.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellQtd);

                    String data = c.getDataRetirada() != null ? c.getDataRetirada().format(dateFormatter) : "-";
                    PdfPCell cellData = new PdfPCell(new Phrase(data, regularFont));
                    cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellData);

                    table.addCell(new Phrase(c.getJustificativa() != null ? c.getJustificativa() : "", regularFont));
                }
                table.setSpacingAfter(20);
                document.add(table);
            }

            // Resumo Financeiro e Quantidades
            Paragraph resumo = new Paragraph();
            resumo.setFont(regularFont);
            resumo.add(new Phrase("Resumo do Relatório:\n", boldFont));
            resumo.add(new Phrase("Total de Itens Consumidos: ", boldFont));
            resumo.add(relatorio.getTotalQuantidade() != null ? String.valueOf(relatorio.getTotalQuantidade()) : "0");
            resumo.add("\n");
            resumo.add(new Phrase("Custo Total Estimado: ", boldFont));
            resumo.add(relatorio.getCustoTotal() != null ? String.format("R$ %.2f", relatorio.getCustoTotal()) : "R$ 0,00");

            document.add(resumo);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new BusinessException("Erro ao gerar arquivo PDF do relatório: " + e.getMessage());
        }
    }
}