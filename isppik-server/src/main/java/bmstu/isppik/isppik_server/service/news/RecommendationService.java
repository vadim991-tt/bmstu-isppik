package bmstu.isppik.isppik_server.service.news;

import bmstu.isppik.isppik_server.model.news.UserInteraction;
import bmstu.isppik.isppik_server.model.news.UserRecommendation;
import bmstu.isppik.isppik_server.model.news.id.UserRecommendationId;
import bmstu.isppik.isppik_server.model.news.news.ActionType;
import bmstu.isppik.isppik_server.repository.news.NewsRepository;
import bmstu.isppik.isppik_server.repository.news.UserInteractionRepository;
import bmstu.isppik.isppik_server.repository.news.UserRecommendationRepository;
import bmstu.isppik.isppik_server.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final UserInteractionRepository userInteractionRepository;
    private final UserRecommendationRepository userRecommendationRepository;

    /**
     * Пересчет SVD раз в 6 часов.
     */
    @Scheduled(fixedRate = 500)  // Запуск раз в 6 часов
    @Transactional
    public void recalculateRecommendations() {

        final List<UserInteraction> interactions = userInteractionRepository.findAll();

        if (interactions.isEmpty()) {
            return; // Если нет данных, нет смысла пересчитывать SVD
        }

        // Уникальные ID пользователей и новостей (с индексами от 0)
        final Map<Long, Integer> userIndex = createSequentialIndex(
                interactions,
                UserInteraction::getUserId
        );
        final Map<Long, Integer> newsIndex = createSequentialIndex(
                interactions,
                UserInteraction::getNewsId
        );

        if (userIndex.isEmpty() || newsIndex.isEmpty()) {
            return; // Если нет пользователей или новостей, SVD не нужен
        }

        final int userCount = userIndex.size();
        final int newsCount = newsIndex.size();

        // Создание разреженной матрицы (по умолчанию все 0)
        RealMatrix interactionMatrix = new Array2DRowRealMatrix(userCount, newsCount);

        for (UserInteraction interaction : interactions) {
            Integer uIdx = userIndex.get(interaction.getUserId());
            Integer nIdx = newsIndex.get(interaction.getNewsId());

            if (uIdx != null && nIdx != null) {
                double rating = convertActionToRating(interaction.getAction());
                interactionMatrix.setEntry(uIdx, nIdx, rating);
            }
        }

        // Проверка, есть ли ненулевые значения в матрице
        if (Arrays.stream(interactionMatrix.getData()).allMatch(row -> Arrays.stream(row).allMatch(v -> v == 0))) {
            return; // Если все 0, SVD делать не нужно
        }

        // Выполняем SVD разложение
        final SingularValueDecomposition svd =
                new SingularValueDecomposition(interactionMatrix);
        final RealMatrix predictedMatrix =
                svd
                        .getU()
                        .multiply(svd.getS())
                        .multiply(svd.getVT());

        // Сохраняем предсказания в БД
        final List<UserRecommendation> recommendations = new ArrayList<>();
        for (Map.Entry<Long, Integer> userEntry : userIndex.entrySet()) {
            final long userId = userEntry.getKey();
            final int userRow = userEntry.getValue();

            for (Map.Entry<Long, Integer> newsEntry : newsIndex.entrySet()) {
                final long newsId = newsEntry.getKey();
                final int newsCol = newsEntry.getValue();
                final double predictedRating = predictedMatrix.getEntry(userRow, newsCol);

                recommendations.add(new UserRecommendation(new UserRecommendationId(userId, newsId), predictedRating));
            }
        }

        // Очистка и сохранение предсказаний
        userRecommendationRepository.deleteAll();
        userRecommendationRepository.saveAll(recommendations);
    }

    /**
     * Создание последовательного индекса ID → номер строки/колонки в матрице.
     */
    private Map<Long, Integer> createSequentialIndex(List<UserInteraction> interactions,
                                                     Function<UserInteraction, Long> idExtractor) {
        List<Long> uniqueIds = interactions.stream()
                .map(idExtractor)
                .distinct()
                .collect(Collectors.toList());

        Map<Long, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < uniqueIds.size(); i++) {
            indexMap.put(uniqueIds.get(i), i);
        }
        return indexMap;
    }

    /**
     * Конвертирует действие пользователя в числовой рейтинг.
     */
    private double convertActionToRating(ActionType action) {
        return switch (action) {
            case LIKE -> 1.0;
            case DISLIKE -> -1.0;
            case VIEW -> 0.5;
        };
    }
}
