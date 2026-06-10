package br.com.ifba.ecologic_back_end.modulos.setor.entity;

import br.com.ifba.ecologic_back_end.modulos.usuario.entity.Usuario;
import br.com.ifba.ecologic_back_end.modulos.consumo.entity.Consumo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "setores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome; // Exemplos: Limpeza, Cozinha, Administrativo

    @Column(length = 255)
    private String descricao;

    // Relacionamento: Vários setores podem ter o mesmo administrador responsável.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_administrador_id", nullable = false)
    private Usuario administrador;

    // Relacionamento: Um setor possui vários registros de consumos.
    // O 'mappedBy = "setor"' indica que o mapeamento principal e a chave estrangeira estão lá na classe Consumo.
    @OneToMany(mappedBy = "setor", fetch = FetchType.LAZY)
    private List<Consumo> consumos = new ArrayList<>();

    // O relacionamento com 'Meta' será adicionado aqui assim que ele finalizar a classe dele.
}