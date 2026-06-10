package br.com.ifba.ecologic_back_end.modulos.usuario.entity;

import br.com.ifba.ecologic_back_end.infraestrutura.persistence.PersistenceEntity;
import br.com.ifba.ecologic_back_end.modulos.usuario.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends PersistenceEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    @Column(name = "cargo")
    private String cargo; // Usado quando tipo = ADMINISTRADOR

    @Column(name = "titulacao")
    private String titulacao; // Usado quando tipo = DIRETOR
}