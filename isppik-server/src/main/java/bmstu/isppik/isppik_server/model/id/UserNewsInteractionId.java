package bmstu.isppik.isppik_server.model.id;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserNewsInteractionId implements Serializable {

    private Long userId;

    private Long newsId;

    // Конструкторы
    public UserNewsInteractionId() {}

    public UserNewsInteractionId(Long userId, Long newsId) {
        this.userId = userId;
        this.newsId = newsId;
    }


}
