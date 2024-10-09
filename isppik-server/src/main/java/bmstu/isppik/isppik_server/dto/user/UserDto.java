package com.example.myapp.dto;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password; // Не используем при возврате данных пользователю
}
