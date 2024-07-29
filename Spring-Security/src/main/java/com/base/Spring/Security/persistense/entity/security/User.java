package com.base.Spring.Security.persistense.entity.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String name;

    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // Obtenemos todos los permisos
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role == null) return null;

        if(role.getPermissions() == null) return null;

        List<SimpleGrantedAuthority> authorities = role.getPermissions().stream()
                .map(each -> each.getOperation().getName())
                .map(each -> new SimpleGrantedAuthority(each))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE" + this.role.getName()));
        return authorities;
    }
    // Obtenemos el password
    @Override
    public String getPassword() {
        return password;
    }
    // Para obtener el nombre de usuario
    @Override
    public String getUsername() {
        return username;
    }
    // Para saber si esta cuanta NO ha expirado
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // Para saber si las credenciales NO estan bloqueadas
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // Para saber si las credenciales NO han expirado
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // Saber si esta habilitado
    @Override
    public boolean isEnabled() {
        return true;
    }
}
