package bmstu.isppik.isppik_server.controller.news;


import bmstu.isppik.isppik_server.dto.news.NewsDto;
import bmstu.isppik.isppik_server.helper.UserHelper;
import bmstu.isppik.isppik_server.service.news.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Контроллер для получение новостей.
 * Содержит вызовы для получение новостей.
 */
@RestController
@RequestMapping("/api/v1/news")
@AllArgsConstructor
public class NewsController {

    private final NewsService newsService;


    // Получение всех новостей
    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNews(
            Principal principal,
            @RequestParam(defaultValue = "10") int limit) {
        
        final Long userId = UserHelper.getUserIdFromPrincipal(principal);
        final List<NewsDto> newsList = newsService.getAllNews(userId, limit);
        return ResponseEntity.ok(newsList);
    }

    // Получение новостей по подпискам
    @GetMapping("/subscriptions")
    public ResponseEntity<List<NewsDto>> getSubscribedNews(
            Principal principal,
            @RequestParam(defaultValue = "10") int limit) {
        
        final Long userId = UserHelper.getUserIdFromPrincipal(principal);
        final List<NewsDto> newsList = newsService.getSubscribedNews(userId, limit);
        return ResponseEntity.ok(newsList);
    }


    // Получение рекомендованных новостей
    @GetMapping("/recommended")
    public ResponseEntity<List<NewsDto>> getRecommendedNews(
            Principal principal,
            @RequestParam(defaultValue = "10") int limit) {
        
        final Long userId = UserHelper.getUserIdFromPrincipal(principal);
        final List<NewsDto> newsList = newsService.getRecommendedNews(userId, limit);
        return ResponseEntity.ok(newsList);
    }





}
