package bmstu.isppik.isppik_server.service.news;

import bmstu.isppik.isppik_server.model.news.NewsItem;
import bmstu.isppik.isppik_server.model.news.UserInteraction;
import bmstu.isppik.isppik_server.model.news.UserRecommendation;
import bmstu.isppik.isppik_server.model.news.id.UserRecommendationId;
import bmstu.isppik.isppik_server.model.news.news.ActionType;
import bmstu.isppik.isppik_server.model.users.User;
import bmstu.isppik.isppik_server.repository.news.NewsRepository;
import bmstu.isppik.isppik_server.repository.news.UserInteractionRepository;
import bmstu.isppik.isppik_server.repository.news.UserRecommendationRepository;
import bmstu.isppik.isppik_server.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final UserRepository userRepository;

    private final NewsRepository newsRepository;

    private final UserInteractionRepository userInteractionRepository;

    private final UserRecommendationRepository userRecommendationRepository;

    /**
     * Периодически пересчитывает рекомендации на основе коллаборативной фильтрации.
     * Используется метрика косинусного сходства.
     */
    @Scheduled(fixedRate = 6 * 60 * 60 * 1000)
    @Transactional
    public void recalculateRecommendations() {
        final List<User> users = userRepository.findAll();
        final List<NewsItem> newsItems = newsRepository.findAll();
        final List<UserInteraction> interactions = userInteractionRepository.findAll();

        if (users.isEmpty() || newsItems.isEmpty()) return;

        Map<Long, Integer> userIndex = indexMap(users, User::getId);
        Map<Long, Integer> newsIndex = indexMap(newsItems, NewsItem::getId);

        int userCount = users.size();
        int newsCount = newsItems.size();

        RealMatrix interactionMatrix = new Array2DRowRealMatrix(userCount, newsCount);
        for (UserInteraction interaction : interactions) {
            int uIdx = userIndex.get(interaction.getUserId());
            int nIdx = newsIndex.get(interaction.getNewsId());
            double rating = convertActionToRating(interaction.getAction());
            interactionMatrix.setEntry(uIdx, nIdx, rating);
        }

        RealMatrix similarityMatrix = computeUserSimilarity(interactionMatrix);
        RealMatrix predictedRatings = predictRatings(similarityMatrix, interactionMatrix);

        Map<Long, Set<Long>> viewed = interactions.stream()
                .collect(Collectors.groupingBy(UserInteraction::getUserId,
                        Collectors.mapping(UserInteraction::getNewsId, Collectors.toSet())));

        List<UserRecommendation> recommendations = new ArrayList<>();
        for (User user : users) {
            int uIdx = userIndex.get(user.getId());
            for (NewsItem news : newsItems) {
                int nIdx = newsIndex.get(news.getId());
                double score = predictedRatings.getEntry(uIdx, nIdx);

                // Исключаем просмотренные и те, что с отрицательной оценкой
                if (viewed.getOrDefault(user.getId(), Set.of()).contains(news.getId())) {
                    continue;
                }
                if (score <= 0) {
                    continue;
                }

                recommendations.add(new UserRecommendation(
                        new UserRecommendationId(user.getId(), news.getId()), score));
            }
        }

        userRecommendationRepository.deleteAllInBatch();
        userRecommendationRepository.saveAll(recommendations);
    }

    /**
     * Вычисляет косинусное сходство между всеми пользователями.
     * <p>
     * Формула:
     * cosine_similarity(A, B) = (A · B) / (||A|| * ||B||)
     * где:
     * - A · B — скалярное произведение векторов (сумма произведений компонент)
     * - ||A|| — длина вектора A (норма)
     * - ||B|| — длина вектора B (норма)
     *
     * @param matrix матрица взаимодействий (пользователи × новости)
     * @return матрица сходства пользователей
     */
    private RealMatrix computeUserSimilarity(RealMatrix matrix) {
        int size = matrix.getRowDimension();
        RealMatrix similarity = new Array2DRowRealMatrix(size, size);

        for (int i = 0; i < size; i++) {
            RealVector vecA = matrix.getRowVector(i);
            for (int j = 0; j < size; j++) {
                RealVector vecB = matrix.getRowVector(j);
                double dot = vecA.dotProduct(vecB);
                double norm = vecA.getNorm() * vecB.getNorm();
                similarity.setEntry(i, j, norm == 0 ? 0 : dot / norm);
            }
        }

        return similarity;
    }

    /**
     * Предсказывает рейтинг новостей для пользователей на основе сходства.
     */
    private RealMatrix predictRatings(RealMatrix similarity, RealMatrix interaction) {
        int userCount = similarity.getRowDimension();
        int newsCount = interaction.getColumnDimension();

        RealMatrix result = new Array2DRowRealMatrix(userCount, newsCount);

        for (int u = 0; u < userCount; u++) {
            for (int n = 0; n < newsCount; n++) {
                double num = 0;
                double denom = 0;

                for (int v = 0; v < userCount; v++) {
                    double sim = similarity.getEntry(u, v);
                    double rating = interaction.getEntry(v, n);
                    num += sim * rating;
                    denom += Math.abs(sim);
                }

                result.setEntry(u, n, denom == 0 ? 0 : num / denom);
            }
        }

        return result;
    }

    private double convertActionToRating(ActionType action) {
        return switch (action) {
            case LIKE -> 1.0;
            case VIEW -> 0.5;
            case DISLIKE -> -1.0;
        };
    }

    private <T> Map<Long, Integer> indexMap(List<T> items, java.util.function.Function<T, Long> extractor) {
        Map<Long, Integer> index = new HashMap<>();
        for (int i = 0; i < items.size(); i++) {
            index.put(extractor.apply(items.get(i)), i);
        }
        return index;
    }
}
