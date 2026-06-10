package br.com.ifba.ecologic_back_end.modulos.usuario.service;

import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioAdministradorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioDiretorRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.request.UsuarioUpdateRequestDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioAdministradorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioDiretorResponseDTO;
import br.com.ifba.ecologic_back_end.modulos.usuario.dto.response.UsuarioResponseDTO;
import java.util.List;

/**
 * Interface de serviço para o módulo Usuário.
 * Define os contratos CRUD para usuários.
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

    /**
     * Obtém um usuário pelo nome (independente do tipo).
     *
     * @param nome nome do usuário
     * @return dados do usuário
     */
    UsuarioResponseDTO getUsuario(String nome);

    /**
     * Lista todos os usuários.
     *
     * @return lista de todos os usuários
     */
    List<UsuarioResponseDTO> listarTodos();

    /**
     * Lista todos os usuários administradores.
     *
     * @return lista de administradores
     */
    List<UsuarioAdministradorResponseDTO> listarAdministradores();

    /**
     * Lista todos os usuários diretores.
     *
     * @return lista de diretores
     */
    List<UsuarioDiretorResponseDTO> listarDiretores();

    /**
     * Atualiza um usuário pelo nome (independente do tipo).
     *
     * @param nome nome do usuário
     * @param dto dados para atualização
     * @return dados do usuário atualizado
     */
    UsuarioResponseDTO atualizarUsuario(String nome, UsuarioUpdateRequestDTO dto);

    /**
     * Deleta um usuário pelo nome (independente do tipo).
     *
     * @param nome nome do usuário
     */
    void deletarUsuario(String nome);
}