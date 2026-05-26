package br.com.ifba.ecologic_back_end.modulos.usuario.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class UsuarioDiretorResponseDTO {

    private UUID id;
    private String email;
    private String titulacao;
    private LocalDateTime dataCriacao;
}
