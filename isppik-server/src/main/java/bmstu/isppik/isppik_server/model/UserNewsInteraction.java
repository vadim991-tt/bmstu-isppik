package bmstu.isppik.isppik_server.model;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import bmstu.isppik.isppik_server.model.id.UserNewsInteractionId;

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
