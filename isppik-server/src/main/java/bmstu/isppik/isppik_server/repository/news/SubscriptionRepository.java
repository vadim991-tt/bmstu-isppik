package bmstu.isppik.isppik_server.repository.news;

import bmstu.isppik.isppik_server.model.news.Subscription;
import bmstu.isppik.isppik_server.model.news.id.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {

    // Удаление подписки по пользователю и источнику
    void deleteByUserIdAndSourceId(Long userId, Long sourceId);

    // Проверка наличия подписки по пользователю и источнику
    boolean existsByUserIdAndSourceId(Long userId, Long sourceId);

    // Получение ID источников, на которые подписан пользователь
    @Query("SELECT s.sourceId FROM Subscription s WHERE s.userId = ?1")
    List<Long> findSubscribedSourceIdsByUserId(Long userId);
}
