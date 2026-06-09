package br.com.ifba.ecologic_back_end.modulos.setor.dto;

import lombok.Data;

@Data
public class SetorResponseDto {
    private Long id;
    private String nome;
    private String descricao;
}