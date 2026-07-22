package br.com.ifba.ecologic_back_end.modulos.produto.entity;

import br.com.ifba.ecologic_back_end.infraestrutura.persistence.PersistenceEntity;
import br.com.ifba.ecologic_back_end.modulos.consumo.entity.Consumo;
import br.com.ifba.ecologic_back_end.modulos.categoria.entity.Categoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "produto")
@Getter
@Setter
public class Produto extends PersistenceEntity {

    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(name = "custo_unitario", nullable = false)
    private double custoUnitario;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<Consumo> consumos;
}