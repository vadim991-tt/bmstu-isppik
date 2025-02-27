package bmstu.isppik.isppik_server.service.news;

import bmstu.isppik.isppik_server.model.news.UserNewsInteraction;
import bmstu.isppik.isppik_server.repository.news.UserNewsInteractionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserInteractionsService {

    private final UserNewsInteractionRepository interactionRepository;

    // Лайк новости
    public void likeNews(Long userId, Long newsId) {
        saveUserNewsInteraction(userId, newsId, "LIKE");
    }

    // Дизлайк новости
    public void dislikeNews(Long userId, Long newsId) {
        saveUserNewsInteraction(userId, newsId, "DISLIKE");
    }

    // Просмотр новости
    public void viewNews(Long userId, Long newsId) {
        saveUserNewsInteraction(userId, newsId, "VIEW");
    }

    // Сохранение взаимодействия пользователя с новостью
    private void saveUserNewsInteraction(Long userId, Long newsId, String action) {
        UserNewsInteraction interaction = new UserNewsInteraction();
        interaction.setUserId(userId);
        interaction.setNewsId(newsId);
        interaction.setAction(action);
        interaction.setTimestamp(LocalDateTime.now());
        interactionRepository.save(interaction);
    }


}
