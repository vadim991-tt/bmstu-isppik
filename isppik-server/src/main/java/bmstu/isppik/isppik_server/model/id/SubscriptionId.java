package bmstu.isppik.isppik_server.model.id;

import lombok.Data;

import java.io.Serializable;

;

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
