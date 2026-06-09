package br.com.ifba.ecologic_back_end.modulos.setor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    // Os relacionamentos com 'UsuarioAdministrador', 'Meta' e 'Consumo'
    // serão adicionados aqui conforme avançarmos no desenvolvimento.
}