package com.example.myapp.model;

import java.io.Serializable;
import java.util.Objects;

public class UserNewsInteractionId implements Serializable {

    private Long userId;
    private Long newsId;

    // Конструкторы
    public UserNewsInteractionId() {}

    public UserNewsInteractionId(Long userId, Long newsId) {
        this.userId = userId;
        this.newsId = newsId;
    }

    // equals и hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserNewsInteractionId that = (UserNewsInteractionId) o;

        return Objects.equals(userId, that.userId) &&
               Objects.equals(newsId, that.newsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, newsId);
    }
}
