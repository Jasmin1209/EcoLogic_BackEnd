package br.com.ifba.ecologic_back_end.modulos.categoria.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CategoriaResponseDTO {

    private UUID id; // UUID herdado da PersistenceEntity

    private String nome;

    private String descricao;
}