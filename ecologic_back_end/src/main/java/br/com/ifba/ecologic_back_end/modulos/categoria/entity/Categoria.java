package br.com.ifba.ecologic_back_end.modulos.categoria.entity;

import br.com.ifba.ecologic_back_end.infraestrutura.persistence.PersistenceEntity;
import br.com.ifba.ecologic_back_end.modulos.produto.entity.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categoria")
@Getter
@Setter
public class Categoria extends PersistenceEntity {

    // O unique = true impede que o usuário cadastre duas categorias com o mesmo nome
    @Column(nullable = false, unique = true)
    private String nome;

    // Um campo extra legal para o professor ver no CRUD
    @Column(length = 255)
    private String descricao;

    // Relacionamento Bi-direcional: Uma Categoria tem vários Produtos.
    // O @JsonIgnore impede o erro de loop infinito na hora de enviar para o React.
    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private List<Produto> produtos;
}