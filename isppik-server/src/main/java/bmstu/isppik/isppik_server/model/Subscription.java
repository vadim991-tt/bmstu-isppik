package com.example.myapp.model;

import javax.persistence.*;

@Data
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String type; // "CATEGORY" или "SOURCE"
    private Long targetId; // ID категории или источника

}
