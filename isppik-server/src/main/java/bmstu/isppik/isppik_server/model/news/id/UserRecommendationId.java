package bmstu.isppik.isppik_server.model.news.id;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRecommendationId implements Serializable {

    private Long userId;

    private Long newsId;
}