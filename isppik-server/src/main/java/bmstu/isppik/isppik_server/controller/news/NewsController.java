package bmstu.isppik.isppik_server.controller.news;


import bmstu.isppik.isppik_server.dto.news.NewsDto;
import bmstu.isppik.isppik_server.helper.UserHelper;
import bmstu.isppik.isppik_server.service.news.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    // Получение всех новостей с курсорной пагинацией по времени
    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNews(
            Principal principal,
            @RequestParam(required = false) LocalDateTime cursorDate,
            @RequestParam(defaultValue = "10") int limit) {
        
        Long userId = UserHelper.getUserIdFromPrincipal(principal);
        
        if (cursorDate == null) {
            cursorDate = LocalDateTime.now();  // Если курсор не указан, использовать текущее время
        }

        List<NewsDto> newsList = newsService.getAllNews(userId, cursorDate, limit);
        return ResponseEntity.ok(newsList);
    }

    // Получение новостей по подпискам с курсорной пагинацией по времени
    @GetMapping("/subscriptions")
    public ResponseEntity<List<NewsDto>> getSubscribedNews(
            Principal principal,
            @RequestParam(required = false) LocalDateTime cursorDate,
            @RequestParam(defaultValue = "10") int limit) {
        
        final Long userId = UserHelper.getUserIdFromPrincipal(principal);
        
        if (cursorDate == null) {
            cursorDate = LocalDateTime.now();  // Если курсор не указан, использовать текущее время
        }

        List<NewsDto> newsList = newsService.getSubscribedNews(userId, cursorDate, limit);
        return ResponseEntity.ok(newsList);
    }

    // Получение рекомендованных новостей с курсорной пагинацией по времени
    @GetMapping("/recommended")
    public ResponseEntity<List<NewsDto>> getRecommendedNews(
            Principal principal,
            @RequestParam(required = false) LocalDateTime cursorDate,
            @RequestParam(defaultValue = "10") int limit) {
        
        final Long userId = UserHelper.getUserIdFromPrincipal(principal);
        if (cursorDate == null) {
            cursorDate = LocalDateTime.now();  // Если курсор не указан, использовать текущее время
        }

        List<NewsDto> newsList = newsService.getRecommendedNews(userId, cursorDate, limit);
        return ResponseEntity.ok(newsList);
    }





}
