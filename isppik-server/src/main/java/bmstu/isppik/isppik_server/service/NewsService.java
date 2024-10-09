package bmstu.isppik.isppik_server.service;

import bmstu.isppik.isppik_server.dto.NewsDto;
import bmstu.isppik.isppik_server.model.NewsItem;
import bmstu.isppik.isppik_server.model.Subscription;
import bmstu.isppik.isppik_server.model.UserNewsInteraction;
import bmstu.isppik.isppik_server.repository.NewsRepository;
import bmstu.isppik.isppik_server.repository.SubscriptionRepository;
import bmstu.isppik.isppik_server.repository.UserNewsInteractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserNewsInteractionRepository interactionRepository;

    // Получение всех новостей с курсорной пагинацией по времени и исключением просмотренных новостей
    public List<NewsDto> getAllNews(Long userId, LocalDateTime cursorDate, int limit) {
        List<Long> viewedNewsIds = interactionRepository.findViewedNewsIdsByUserId(userId);
        List<NewsItem> newsItems = newsRepository.findNewsBeforeDateExcludingViewed(cursorDate, viewedNewsIds, limit);
        return newsItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Получение новостей по подпискам с курсорной пагинацией по времени
    public List<NewsDto> getSubscribedNews(Long userId, LocalDateTime cursorDate, int limit) {
        List<Long> subscribedSourceIds = subscriptionRepository.findSubscribedSourceIdsByUserId(userId);
        if (subscribedSourceIds.isEmpty()) {
            return List.of(); // Возвращаем пустой список, если нет подписок
        }
        List<Long> viewedNewsIds = interactionRepository.findViewedNewsIdsByUserId(userId);
        List<NewsItem> newsItems = newsRepository.findSubscribedNewsBeforeDateExcludingViewed(subscribedSourceIds, cursorDate, viewedNewsIds, limit);
        return newsItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Получение рекомендованных новостей с курсорной пагинацией по времени
    public List<NewsDto> getRecommendedNews(Long userId, LocalDateTime cursorDate, int limit) {
        List<Long> recommendedIds = getRecommendedNewsIds(userId, limit);
        if (recommendedIds.isEmpty()) {
            return List.of(); // Возвращаем пустой список, если нет рекомендаций
        }
        List<Long> viewedNewsIds = interactionRepository.findViewedNewsIdsByUserId(userId);
        List<NewsItem> newsItems = newsRepository.findRecommendedNewsBeforeDateExcludingViewed(recommendedIds, cursorDate, viewedNewsIds, limit);
        return newsItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Подписка на источник новостей
    public void subscribe(Long userId, Long sourceId) {
        if (!subscriptionRepository.existsByUserIdAndSourceId(userId, sourceId)) {
            Subscription subscription = new Subscription();
            subscription.setUserId(userId);
            subscription.setSourceId(sourceId);
            subscriptionRepository.save(subscription);
        }
    }

    // Отписка от источника новостей
    public void unsubscribe(Long userId, Long sourceId) {
        subscriptionRepository.deleteByUserIdAndSourceId(userId, sourceId);
    }

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

    // Получение списка рекомендованных новостей (заглушка)
    private List<Long> getRecommendedNewsIds(Long userId, int limit) {
        // Здесь вы можете реализовать вызов рекомендательной системы
        return List.of(); // Заглушка, замените на реальную логику
    }

    // Преобразование NewsItem в DTO
    private NewsDto convertToDto(NewsItem newsItem) {
        NewsDto dto = new NewsDto();
        dto.setId(newsItem.getId());
        dto.setTitle(newsItem.getTitle());
        dto.setContent(newsItem.getContent());
        dto.setLink(newsItem.getLink());
        dto.setPublishedDate(newsItem.getPublishedDate());
        dto.setSourceId(newsItem.getSourceId());
        return dto;
    }
}
