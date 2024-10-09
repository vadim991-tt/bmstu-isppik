package mbstu.isppik.isppik_server.controller;

import mbstu.isppik.isppik_server.dto.NewsDto;
import mbstu.isppik.isppik_server.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // Получение всех новостей с курсорной пагинацией по времени
    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNews(
            Principal principal,
            @RequestParam(required = false) LocalDateTime cursorDate,
            @RequestParam(defaultValue = "10") int limit) {
        
        Long userId = getUserIdFromPrincipal(principal);
        
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
        
        Long userId = getUserIdFromPrincipal(principal);
        
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
        
        Long userId = getUserIdFromPrincipal(principal);
        
        if (cursorDate == null) {
            cursorDate = LocalDateTime.now();  // Если курсор не указан, использовать текущее время
        }

        List<NewsDto> newsList = newsService.getRecommendedNews(userId, cursorDate, limit);
        return ResponseEntity.ok(newsList);
    }

    // Подписка на источник новостей
    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(Principal principal, @RequestParam Long sourceId) {
        Long userId = getUserIdFromPrincipal(principal);
        newsService.subscribe(userId, sourceId);
        return ResponseEntity.ok("Subscribed successfully");
    }

    // Отписка от источника новостей
    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(Principal principal, @RequestParam Long sourceId) {
        Long userId = getUserIdFromPrincipal(principal);
        newsService.unsubscribe(userId, sourceId);
        return ResponseEntity.ok("Unsubscribed successfully");
    }

    // Лайк новости
    @PostMapping("/{id}/like")
    public ResponseEntity<?> likeNews(@PathVariable Long id, Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        newsService.likeNews(userId, id);
        return ResponseEntity.ok("News liked");
    }

    // Дизлайк новости
    @PostMapping("/{id}/dislike")
    public ResponseEntity<?> dislikeNews(@PathVariable Long id, Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        newsService.dislikeNews(userId, id);
        return ResponseEntity.ok("News disliked");
    }

    // Просмотр новости
    @PostMapping("/{id}/view")
    public ResponseEntity<?> viewNews(@PathVariable Long id, Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        newsService.viewNews(userId, id);
        return ResponseEntity.ok("News viewed");
    }

    private Long getUserIdFromPrincipal(Principal principal) {
        return Long.parseLong(principal.getName());
    }
}
