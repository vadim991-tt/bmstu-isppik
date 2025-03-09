package bmstu.isppik.isppik_server.repository.news;

import bmstu.isppik.isppik_server.model.news.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsRepository extends JpaRepository<NewsItem, Long> {

    /**
     * Получает все новости, исключая уже просмотренные пользователем
     */
    @Query(value = """
        SELECT n.* FROM news_items n
        WHERE n.published_date > :cursorDate
        AND NOT EXISTS (
            SELECT 1 FROM user_news_interactions ui
            WHERE ui.news_id = n.id AND ui.user_id = :userId
        )
        ORDER BY n.published_date DESC
        LIMIT :limit
    """, nativeQuery = true)
    List<NewsItem> findAllNewsBeforeDateExcludingViewed(
            @Param("userId") Long userId,
            @Param("cursorDate") LocalDateTime cursorDate,
            @Param("limit") int limit
    );

    /**
     * Получает новости из подписок пользователя, исключая уже просмотренные
     */
    @Query(value = """
        SELECT n.* FROM news_items n
        JOIN subscriptions s ON n.source_id = s.source_id
        WHERE s.user_id = :userId
        AND n.published_date > :cursorDate
        AND NOT EXISTS (
            SELECT 1 FROM user_news_interactions ui
            WHERE ui.news_id = n.id AND ui.user_id = :userId
        )
        ORDER BY n.published_date DESC
        LIMIT :limit
    """, nativeQuery = true)
    List<NewsItem> findSubscribedNewsBeforeDateExcludingViewed(
            @Param("userId") Long userId,
            @Param("cursorDate") LocalDateTime cursorDate,
            @Param("limit") int limit
    );

    /**
     * Получает рекомендованные новости, исключая уже просмотренные
     */
    @Query(value = """
        SELECT n.* FROM news_items n
        JOIN user_recommendations ur ON n.id = ur.news_id
        WHERE ur.user_id = :userId
        AND n.published_date > :cursorDate
        AND NOT EXISTS (
            SELECT 1 FROM user_news_interactions ui
            WHERE ui.news_id = n.id AND ui.user_id = :userId
        )
        ORDER BY ur.predicted_rating DESC
        LIMIT :limit
    """, nativeQuery = true)
    List<NewsItem> findRecommendedNewsBeforeDate(
            @Param("userId") Long userId,
            @Param("cursorDate") LocalDateTime cursorDate,
            @Param("limit") int limit
    );
}
