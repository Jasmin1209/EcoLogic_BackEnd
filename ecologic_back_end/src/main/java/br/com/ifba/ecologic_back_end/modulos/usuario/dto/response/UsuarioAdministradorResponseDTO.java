package br.com.ifba.ecologic_back_end.modulos.usuario.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class UsuarioAdministradorResponseDTO {

    private UUID id;
    private String nome;
    private String email;
    private String cargo;
    private LocalDateTime dataCriacao;
}
