package br.com.ifba.ecologic_back_end.modulos.usuario.service;

import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioAdministradorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioDiretorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioAdministradorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioDiretorResponseDTO;

/**
 * Interface de serviço para o módulo Usuário.
 * Define os contratos de criação para cada tipo de usuário.
 */
public interface UsuarioIService {

    /**
     * Cria um usuário administrador no sistema.
     *
     * @param dto dados do administrador a ser criado
     * @return dados do administrador criado
     */
    UsuarioAdministradorResponseDTO criarAdministrador(UsuarioAdministradorRequestDTO dto);

    /**
     * Cria um usuário diretor no sistema.
     *
     * @param dto dados do diretor a ser criado
     * @return dados do diretor criado
     */
    UsuarioDiretorResponseDTO criarDiretor(UsuarioDiretorRequestDTO dto);
}