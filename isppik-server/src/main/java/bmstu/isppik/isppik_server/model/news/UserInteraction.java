package bmstu.isppik.isppik_server.model.news;

import bmstu.isppik.isppik_server.model.news.news.ActionType;
import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import bmstu.isppik.isppik_server.model.news.id.UserInteractionId;

@Entity
@Table(name = "user_news_interactions")
@IdClass(UserInteractionId.class)
@Data
public class UserInteraction {

    @Id
    private Long userId;

    @Id
    private Long newsId;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private ActionType action;

    private LocalDateTime timestamp;
}
