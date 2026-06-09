package br.com.ifba.ecologic_back_end.modulos.consumo.entity;

import br.com.ifba.ecologic_back_end.modulos.produto.entity.Produto;
import br.com.ifba.ecologic_back_end.infraestrutura.persistence.PersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "consumo")
@Getter
@Setter
public class Consumo extends PersistenceEntity {

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private LocalDate dataRetirada;

    @Column(nullable = false)
    private String justificativa;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
