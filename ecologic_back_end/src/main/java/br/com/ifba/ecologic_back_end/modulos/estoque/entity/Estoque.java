package br.com.ifba.ecologic_back_end.modulos.estoque.entity;

import br.com.ifba.ecologic_back_end.modulos.produto.entity.Produto;
import br.com.ifba.ecologic_back_end.infraestrutura.persistence.PersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estoque")
@Getter
@Setter
public class Estoque extends PersistenceEntity {

    @Column(nullable = false)
    private Integer quantidadeAtual;

    @Column(nullable = false)
    private Integer quantidadeMinima;

    @OneToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
