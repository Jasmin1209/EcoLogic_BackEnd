package br.com.ifba.ecologic_back_end.modulos.usuario.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_usuario_diretor")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDiretor extends Usuario {

    private String titulacao;
}