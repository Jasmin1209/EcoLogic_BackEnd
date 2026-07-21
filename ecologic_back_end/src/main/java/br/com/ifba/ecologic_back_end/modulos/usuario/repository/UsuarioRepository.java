package br.com.ifba.ecologic_back_end.modulos.usuario.repository;

import br.com.ifba.ecologic_back_end.modulos.usuario.entity.Usuario;
import br.com.ifba.ecologic_back_end.modulos.usuario.enums.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository JPA para a entidade Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    /**
     * Verifica se ja existe um usuario com o email informado.
     */
    boolean existsByEmail(String email);

    /**
     * Busca um usuario pelo email.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Busca um usuario pelo nome.
     */
    Optional<Usuario> findByNome(String nome);

    /**
     * Lista todos os usuarios de um tipo especifico.
     */
    List<Usuario> findByTipo(TipoUsuario tipo);

    /**
     * Busca um usuario pelo token de redefinição de senha.
     */
    Optional<Usuario> findByTokenRedefinicaoSenha(String token);
}