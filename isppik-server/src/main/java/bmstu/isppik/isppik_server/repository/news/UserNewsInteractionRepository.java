package bmstu.isppik.isppik_server.repository.news;

import bmstu.isppik.isppik_server.model.news.UserNewsInteraction;
import bmstu.isppik.isppik_server.model.news.id.UserNewsInteractionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserNewsInteractionRepository extends JpaRepository<UserNewsInteraction, UserNewsInteractionId> {

    // Получение ID новостей, которые пользователь уже просмотрел
    @Query("SELECT newsId FROM UserNewsInteraction WHERE userId = ?1 AND action = 'VIEW'")
    List<Long> findViewedNewsIdsByUserId(Long userId);
}
