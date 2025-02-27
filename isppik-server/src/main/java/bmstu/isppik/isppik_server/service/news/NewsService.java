package bmstu.isppik.isppik_server.service.news;

import bmstu.isppik.isppik_server.dto.news.NewsDto;
import bmstu.isppik.isppik_server.model.news.NewsItem;
import bmstu.isppik.isppik_server.repository.news.NewsRepository;
import bmstu.isppik.isppik_server.repository.news.SubscriptionRepository;
import bmstu.isppik.isppik_server.repository.news.UserNewsInteractionRepository;
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


    // Получение списка рекомендованных новостей (заглушка)
    private List<Long> getRecommendedNewsIds(Long userId, int limit) {
        // Здесь вы можете реализовать вызов рекомендательной системы
        return List.of(); // Заглушка, замените на реальную логику
    }

    // Преобразование NewsItem в DTO
    private NewsDto convertToDto(NewsItem newsItem) {
        return new NewsDto(newsItem);
    }
}
