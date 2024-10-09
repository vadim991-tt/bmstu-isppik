package bmstu.isppik.isppik_server.repository;

import bmstu.isppik.isppik_server.model.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsRepository extends JpaRepository<NewsItem, Long> {

    // Получение всех новостей до определенной даты и исключение просмотренных новостей
    @Query(value = "SELECT * FROM news_items WHERE published_date < ?1 AND id NOT IN ?2 ORDER BY published_date DESC LIMIT ?3", nativeQuery = true)
    List<NewsItem> findNewsBeforeDateExcludingViewed(LocalDateTime cursorDate, List<Long> viewedNewsIds, int limit);

    // Получение новостей по подпискам до определенной даты и исключение просмотренных новостей
    @Query(value = "SELECT * FROM news_items WHERE published_date < ?1 AND source_id IN ?2 AND id NOT IN ?3 ORDER BY published_date DESC LIMIT ?4", nativeQuery = true)
    List<NewsItem> findSubscribedNewsBeforeDateExcludingViewed(List<Long> subscribedSourceIds, LocalDateTime cursorDate, List<Long> viewedNewsIds, int limit);

    // Получение рекомендованных новостей до определенной даты и исключение просмотренных новостей
    @Query(value = "SELECT * FROM news_items WHERE published_date < ?1 AND id IN ?2 AND id NOT IN ?3 ORDER BY published_date DESC LIMIT ?4", nativeQuery = true)
    List<NewsItem> findRecommendedNewsBeforeDateExcludingViewed(List<Long> recommendedIds, LocalDateTime cursorDate, List<Long> viewedNewsIds, int limit);


    // Проверка наличия новости по ссылке (чтобы избежать дублирования)
    boolean existsByLink(String link);
}
