package bmstu.isppik.isppik_server.controller.news;

import bmstu.isppik.isppik_server.helper.UserHelper;
import bmstu.isppik.isppik_server.service.news.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Контроллер для взаимодействия с системой подписок.
 * Содержит вызовы для взаимодействия с подписками клиен.
 */
@RestController
@RequestMapping("/api/user/v1/subscriptions")
@AllArgsConstructor
public class SubscriptionController {


    private final SubscriptionService subscriptionService;


    // Подписка на источник новостей
    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(Principal principal, @RequestParam Long sourceId) {
        final Long userId = UserHelper.getUserIdFromPrincipal(principal);
        subscriptionService.subscribe(userId, sourceId);
        return ResponseEntity.ok("Subscribed successfully");
    }

    // Отписка от источника новостей
    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(Principal principal, @RequestParam Long sourceId) {
        final Long userId = UserHelper.getUserIdFromPrincipal(principal);
        subscriptionService.unsubscribe(userId, sourceId);
        return ResponseEntity.ok("Unsubscribed successfully");
    }

}
