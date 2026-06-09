package br.com.ifba.ecologic_back_end.modulos.produto.entity;

import br.com.ifba.ecologic_back_end.modulos.produto.entity.enums.Categoria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "produto")
@Getter
@Setter
public class Produto extends br.com.ifba.ecologic_back_end.infraestrutura.persistence.PersistenceEntity {

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Column(name = "unidade_de_medida", nullable = false)
    private String unidadeDeMedida;

    @Column(name = "custo_unitario", nullable = false)
    private double custoUnitario;

    @OneToOne(mappedBy = "produto")
    private Estoque estoque;

    @OneToMany(mappedBy = "produto")
    private List<Consumo> consumos;
}