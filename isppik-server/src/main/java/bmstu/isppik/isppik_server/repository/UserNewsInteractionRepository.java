package com.example.myapp.repository;

import com.example.myapp.model.UserNewsInteraction;
import com.example.myapp.model.UserNewsInteractionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserNewsInteractionRepository extends JpaRepository<UserNewsInteraction, UserNewsInteractionId> {

    // Получение ID новостей, которые пользователь уже просмотрел
    @Query("SELECT newsId FROM UserNewsInteraction WHERE userId = ?1 AND action = 'VIEW'")
    List<Long> findViewedNewsIdsByUserId(Long userId);
}
