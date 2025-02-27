package bmstu.isppik.isppik_server.service.news;

import bmstu.isppik.isppik_server.model.news.Subscription;
import bmstu.isppik.isppik_server.repository.news.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

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

}
