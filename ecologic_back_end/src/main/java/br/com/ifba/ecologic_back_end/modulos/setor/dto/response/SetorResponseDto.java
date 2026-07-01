package br.com.ifba.ecologic_back_end.modulos.setor.dto.response;

import lombok.Data;

import java.util.UUID; // Importação necessária para o UUID

@Data
public class SetorResponseDto {
    private Long id; // Mantemos Long aqui pois o ID do SEU Setor é Long
    private String nome;
    private String descricao;
    private UUID administradorId;
}