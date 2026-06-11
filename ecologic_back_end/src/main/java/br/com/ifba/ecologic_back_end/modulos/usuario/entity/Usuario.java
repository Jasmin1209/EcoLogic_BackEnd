package br.com.ifba.ecologic_back_end.modulos.usuario.entity;

import br.com.ifba.ecologic_back_end.infraestrutura.persistence.PersistenceEntity;
import br.com.ifba.ecologic_back_end.modulos.usuario.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends PersistenceEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "titulacao")
    private String titulacao;

    // --- MÉTODOS DO USERDETAILS --- //

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna a role (perfil) baseado no seu enum de TipoUsuario
        if (this.tipo == TipoUsuario.ADMINISTRADOR) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_DIRETOR"), new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email; // O login é feito pelo email
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}