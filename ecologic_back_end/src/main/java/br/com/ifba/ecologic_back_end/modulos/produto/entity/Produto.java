package br.com.ifba.ecologic_back_end.modulos.produto.entity;
import br.com.ifba.ecologic_back_end.infraestrutura.persistence.PersistenceEntity;
import br.com.ifba.ecologic_back_end.modulos.consumo.entity.Consumo;
import br.com.ifba.ecologic_back_end.modulos.estoque.entity.Estoque;
import br.com.ifba.ecologic_back_end.modulos.produto.entity.enums.Categoria;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Column(name = "unidade_de_medida", nullable = false)
    private String unidadeDeMedida;

    @Column(name = "custo_unitario", nullable = false)
    private double custoUnitario;

    @JsonIgnore
    @OneToOne(mappedBy = "produto")
    private Estoque estoque;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<Consumo> consumos;
}