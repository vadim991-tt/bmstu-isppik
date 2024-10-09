package bmstu.isppik.isppik_server.controller;

import bmstu.isppik.isppik_server.dto.NewsDto;
import bmstu.isppik.isppik_server.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // Загрузка всех новостей
    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNews(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        List<NewsDto> newsList = newsService.getAllNews(page, size);
        return ResponseEntity.ok(newsList);
    }

    // Получение рекомендованных новостей
    @GetMapping("/recommended")
    public ResponseEntity<List<NewsDto>> getRecommendedNews(Principal principal,
                                                            @RequestParam(defaultValue = "10") int limit) {
        Long userId = Long.parseLong(principal.getName());
        List<NewsDto> newsList = newsService.getRecommendedNews(userId, limit);
        return ResponseEntity.ok(newsList);
    }

    // Получение новостей по подпискам
    @GetMapping("/subscriptions")
    public ResponseEntity<List<NewsDto>> getSubscribedNews(Principal principal,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Long userId = Long.parseLong(principal.getName());
        List<NewsDto> newsList = newsService.getSubscribedNews(userId, page, size);
        return ResponseEntity.ok(newsList);
    }

    // Подписка на категорию или источник
    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(Principal principal, @RequestParam String type, @RequestParam Long id) {
        Long userId = Long.parseLong(principal.getName());
        newsService.subscribe(userId, type, id);
        return ResponseEntity.ok("Subscribed successfully");
    }

    // Отписка от категории или источника
    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(Principal principal, @RequestParam String type, @RequestParam Long id) {
        Long userId = Long.parseLong(principal.getName());
        newsService.unsubscribe(userId, type, id);
        return ResponseEntity.ok("Unsubscribed successfully");
    }

    // Лайк новости
    @PostMapping("/{id}/like")
    public ResponseEntity<?> likeNews(@PathVariable Long id, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        newsService.likeNews(userId, id);
        return ResponseEntity.ok("News liked");
    }

    // Дизлайк новости
    @PostMapping("/{id}/dislike")
    public ResponseEntity<?> dislikeNews(@PathVariable Long id, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        newsService.dislikeNews(userId, id);
        return ResponseEntity.ok("News disliked");
    }

    // Просмотр новости
    @PostMapping("/{id}/view")
    public ResponseEntity<?> viewNews(@PathVariable Long id, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        newsService.viewNews(userId, id);
        return ResponseEntity.ok("News viewed");
    }
}
