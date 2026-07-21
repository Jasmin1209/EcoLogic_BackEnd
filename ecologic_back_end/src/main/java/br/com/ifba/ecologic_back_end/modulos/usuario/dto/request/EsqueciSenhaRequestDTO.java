package br.com.ifba.ecologic_back_end.modulos.usuario.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EsqueciSenhaRequestDTO {

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
}