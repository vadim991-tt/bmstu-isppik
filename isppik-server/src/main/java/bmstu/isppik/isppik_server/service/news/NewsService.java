package bmstu.isppik.isppik_server.service.news;

import bmstu.isppik.isppik_server.dto.news.NewsDto;
import bmstu.isppik.isppik_server.model.news.NewsItem;
import bmstu.isppik.isppik_server.repository.news.NewsRepository;
import bmstu.isppik.isppik_server.repository.news.UserRecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    /**
     * Получение всех новостей (без подписок и рекомендаций), с курсорной пагинацией и исключением просмотренных
     */
    public List<NewsDto> getAllNews(Long userId,
                                    LocalDateTime cursorDate,
                                    int limit) {
        return newsRepository.findAllNewsBeforeDateExcludingViewed(userId, cursorDate, limit)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Получение новостей по подпискам с курсорной пагинацией по времени
     */
    public List<NewsDto> getSubscribedNews(Long userId,
                                           LocalDateTime cursorDate,
                                           int limit) {
        return newsRepository.findSubscribedNewsBeforeDateExcludingViewed(userId, cursorDate, limit)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Получение рекомендованных новостей с курсорной пагинацией по времени
     */
    public List<NewsDto> getRecommendedNews(Long userId,
                                            LocalDateTime cursorDate,
                                            int limit) {
        return newsRepository.findRecommendedNewsBeforeDate(userId, cursorDate, limit)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Преобразование NewsItem в DTO
     */
    private NewsDto convertToDto(NewsItem newsItem) {
        return new NewsDto(newsItem);
    }
}
