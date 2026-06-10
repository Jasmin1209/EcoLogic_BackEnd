package br.com.ifba.ecologic_back_end.modulos.usuario.repository;

import br.com.ifba.ecologic_back_end.modulos.usuario.enums.TipoUsuario;
import br.com.ifba.ecologic_back_end.modulos.usuario.entity.Usuario;
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
     * Verifica se já existe um usuário com o email informado.
     */
    boolean existsByEmail(String email);

    /**
     * Busca um usuário pelo nome.
     */
    Optional<Usuario> findByNome(String nome);

    /**
     * Lista todos os usuários de um tipo específico.
     */
    List<Usuario> findByTipo(TipoUsuario tipo);
}