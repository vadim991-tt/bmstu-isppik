package bmstu.isppik.isppik_server.service.news;

import bmstu.isppik.isppik_server.model.news.UserInteraction;
import bmstu.isppik.isppik_server.model.news.news.ActionType;
import bmstu.isppik.isppik_server.repository.news.UserInteractionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserInteractionsService {

    private final UserInteractionRepository interactionRepository;

    // Лайк новости
    public void likeNews(Long userId, Long newsId) {
        saveUserNewsInteraction(userId, newsId, ActionType.LIKE);
    }

    // Дизлайк новости
    public void dislikeNews(Long userId, Long newsId) {
        saveUserNewsInteraction(userId, newsId, ActionType.DISLIKE);
    }

    // Просмотр новости
    public void viewNews(Long userId, Long newsId) {
        saveUserNewsInteraction(userId, newsId, ActionType.VIEW);
    }

    // Сохранение взаимодействия пользователя с новостью
    private void saveUserNewsInteraction(Long userId, Long newsId, ActionType action) {
        UserInteraction interaction = new UserInteraction();
        interaction.setUserId(userId);
        interaction.setNewsId(newsId);
        interaction.setAction(action);
        interaction.setTimestamp(LocalDateTime.now());
        interactionRepository.save(interaction);
    }


}
