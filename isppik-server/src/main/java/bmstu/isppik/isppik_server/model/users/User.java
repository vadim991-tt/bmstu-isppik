package bmstu.isppik.isppik_server.model.users;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Добавлено для автогенерации ID
    private Long id;

    private String username;

    private String password;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    // Конструкторы, геттеры и сеттеры

    public User() {}

    // Реализация методов UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    // Остальные методы интерфейса UserDetails

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Можно добавить логику проверки
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Можно добавить логику проверки
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Можно добавить логику проверки
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    
}
