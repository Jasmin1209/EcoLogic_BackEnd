package br.com.ifba.ecologic_back_end.modulos.usuario.repository;

import br.com.ifba.ecologic_back_end.modulos.usuario.entity.UsuarioDiretor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

/**
 * Repository JPA para a entidade UsuarioDiretor.
 */
@Repository
public interface UsuarioDiretorRepository extends JpaRepository<UsuarioDiretor, UUID> {
}
