package bmstu.isppik.isppik_server.controller.news;

import bmstu.isppik.isppik_server.helper.UserHelper;
import bmstu.isppik.isppik_server.service.news.UserInteractionsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Контроллер для взаимодействия с пользовательскими взаимодействиями
 * Содержит вызовы взаимодействий клиента (лайк/дизлайк/просмотр)
 */
@RestController
@RequestMapping("/api/v1/user-interactions")
@AllArgsConstructor
public class UserInteractionsController {


    private final UserInteractionsService userInteractionService;

    // Лайк новости
    @GetMapping("/{id}/like")
    public ResponseEntity<?> likeNews(@PathVariable Long id, Principal principal) {
        final Long userId = UserHelper.getUserIdFromPrincipal(principal);
        userInteractionService.likeNews(userId, id);
        return ResponseEntity.ok("News liked");
    }

    // Дизлайк новости
    @GetMapping("/{id}/dislike")
    public ResponseEntity<?> dislikeNews(@PathVariable Long id, Principal principal) {
        final Long userId = UserHelper.getUserIdFromPrincipal(principal);
        userInteractionService.dislikeNews(userId, id);
        return ResponseEntity.ok("News disliked");
    }

    // Просмотр новости
    @GetMapping("/{id}/view")
    public ResponseEntity<?> viewNews(@PathVariable Long id, Principal principal) {
        final Long userId = UserHelper.getUserIdFromPrincipal(principal);
        userInteractionService.viewNews(userId, id);
        return ResponseEntity.ok("News viewed");
    }
}
