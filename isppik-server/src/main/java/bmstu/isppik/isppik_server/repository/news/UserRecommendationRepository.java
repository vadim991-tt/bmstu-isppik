package bmstu.isppik.isppik_server.repository.news;

import bmstu.isppik.isppik_server.model.news.UserRecommendation;
import bmstu.isppik.isppik_server.model.news.id.UserRecommendationId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRecommendationRepository extends JpaRepository<UserRecommendation, UserRecommendationId> {

    @Query("""
        SELECT ur FROM UserRecommendation ur
        WHERE ur.id.userId = :userId
        AND ur.id.newsId NOT IN (
            SELECT ui.id.newsId FROM UserInteraction ui WHERE ui.id.userId = :userId
        )
        ORDER BY ur.predictedRating DESC
    """)
    List<UserRecommendation> findTopRecommendations(@Param("userId") Long userId, Pageable pageable);
}