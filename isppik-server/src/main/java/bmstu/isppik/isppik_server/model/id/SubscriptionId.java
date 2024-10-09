package com.example.myapp.model;

import java.io.Serializable;
import java.util.Objects;

@Data
public class SubscriptionId implements Serializable {

    private Long userId;
    private Long sourceId;

    // Конструкторы

    public SubscriptionId() {}

    public SubscriptionId(Long userId, Long sourceId) {
        this.userId = userId;
        this.sourceId = sourceId;
    }


}
