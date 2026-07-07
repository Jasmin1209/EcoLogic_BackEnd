package br.com.ifba.ecologic_back_end.modulos.relatorio.entity;

import br.com.ifba.ecologic_back_end.infraestrutura.persistence.PersistenceEntity;
import br.com.ifba.ecologic_back_end.modulos.setor.entity.Setor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Table(name = "relatorios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relatorio extends PersistenceEntity {


    @Column(nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRelatorio tipoRelatorio;

    @Column(nullable = false)
    private LocalDateTime dataGeracao;

    @Column(nullable = false)
    private LocalDateTime periodoInicio;

    @Column(nullable = false)
    private LocalDateTime periodoFim;

    // Relacionamento: aponta para qual setor puxará as queries
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id", nullable = true)
    private Setor setor;
}