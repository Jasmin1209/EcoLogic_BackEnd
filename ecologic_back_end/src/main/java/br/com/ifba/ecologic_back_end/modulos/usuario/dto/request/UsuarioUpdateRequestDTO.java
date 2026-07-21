package br.com.ifba.ecologic_back_end.modulos.usuario.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para atualização parcial de usuário.
 * Todos os campos são opcionais — apenas os campos informados serão atualizados.
 * O campo atributoEspecifico representa cargo (ADMINISTRADOR) ou titulacao (DIRETOR).
 */
@Data
public class UsuarioUpdateRequestDTO {

    // Nome do usuário (opcional)
    private String nome;

    // Email do usuário (opcional)
    @Email(message = "O email deve ser válido.")
    private String email;

    // Nova senha (opcional, mínimo 6 caracteres se informada)
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    // Cargo (ADMINISTRADOR) ou Titulação (DIRETOR) — opcional
    private String atributoEspecifico;
}

