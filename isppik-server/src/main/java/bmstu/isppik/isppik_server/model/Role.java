package com.example.myapp.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Например, ROLE_USER, ROLE_ADMIN

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    // Конструкторы, геттеры и сеттеры

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

}
