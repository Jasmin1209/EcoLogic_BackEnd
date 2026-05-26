package br.com.ifba.ecologic_back_end.modulos.usuario.repository;

import br.com.ifba.ecologic_back_end.modulos.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

/**
 * Repository JPA para a entidade base Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    /**
     * Verifica se já existe um usuário com o email informado.
     * Utilizado para prevenir cadastros duplicados.
     */
    boolean existsByEmail(String email);
}