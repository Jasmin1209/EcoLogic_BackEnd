package br.com.ifba.ecologic_back_end.modulos.setor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SetorRequestDto {

    @NotBlank(message = "O nome do setor é obrigatório.")
    @Size(max = 100, message = "O nome não pode passar de 100 caracteres.")
    private String nome;

    @Size(max = 255, message = "A descrição não pode passar de 255 caracteres.")
    private String descricao;
}