package eda.projecto.kidtracker.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    private String nome;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_familia")
    private Familia familia;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
      name = "usuario_roles",
      joinColumns = @JoinColumn(name = "usuario_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    //
     public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles; // Agora isto funciona, porque Role implementa GrantedAuthority
    }
    @Override
    public String getPassword() {
        return senhaHash;
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

}
