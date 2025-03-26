package bmstu.isppik.isppik_server.model.news;

import bmstu.isppik.isppik_server.model.news.id.UserRecommendationId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "user_recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRecommendation {

    @EmbeddedId
    private UserRecommendationId id;

    private double predictedRating;
}
