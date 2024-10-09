package com.example.myapp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sources")
@Data
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Название источника

    private String url;   // URL RSS-ленты или API

    private String type;  // Тип источника (например, RSS, API и т.д.)
}
