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


    
}
