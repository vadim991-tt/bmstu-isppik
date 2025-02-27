package bmstu.isppik.isppik_server.model.news;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String type; // "CATEGORY" или "SOURCE"

    private Long sourceId; // ID категории или источника

}
