package br.com.ifba.ecologic_back_end.modulos.usuario.entity;

import br.com.ifba.ecologic_back_end.infraestrutura.persistence.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Usuario extends PersistenceEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;
}