package bmstu.isppik.isppik_server.model.news;

import bmstu.isppik.isppik_server.model.news.id.UserRecommendationId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRecommendation {

    @EmbeddedId
    private UserRecommendationId id;

    private double predictedRating;
}
