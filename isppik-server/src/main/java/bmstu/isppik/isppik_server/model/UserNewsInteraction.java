package com.example.myapp.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_news_interactions")
@IdClass(UserNewsInteractionId.class)
@Data
public class UserNewsInteraction {

    @Id
    private Long userId;

    @Id
    private Long newsId;

    private String action;

    private LocalDateTime timestamp;
}
