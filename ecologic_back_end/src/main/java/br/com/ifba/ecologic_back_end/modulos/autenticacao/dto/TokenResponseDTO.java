package br.com.ifba.ecologic_back_end.modulos.autenticacao.dto;

import java.util.UUID;

public record TokenResponseDTO(String token, UUID id) {
}
