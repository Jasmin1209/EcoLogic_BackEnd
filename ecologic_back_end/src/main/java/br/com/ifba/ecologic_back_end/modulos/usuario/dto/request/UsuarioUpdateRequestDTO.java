package br.com.ifba.ecologic_back_end.modulos.usuario.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO genérico para atualização de usuário.
 * O campo atributoEspecifico é usado para cargo (ADMINISTRADOR) ou titulacao (DIRETOR).
 */
@Data
public class UsuarioUpdateRequestDTO {

    @NotBlank(message = "O atributo específico é obrigatório.")
    private String atributoEspecifico; // cargo ou titulacao
}

