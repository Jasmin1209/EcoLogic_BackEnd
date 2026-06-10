package br.com.ifba.ecologic_back_end.modulos.usuario.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO de resposta genérico para qualquer tipo de usuário.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private UUID id;
    private String nome;
    private String email;
    private String tipo; // "ADMINISTRADOR" ou "DIRETOR"
    private String atributoEspecifico; // cargo (para ADMINISTRADOR) ou titulacao (para DIRETOR)
    private LocalDateTime dataCriacao;
}

