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
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.linear.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendationService {

    private final UserInteractionRepository interactionRepository;
    private final UserRecommendationRepository recommendationRepository;


    @Scheduled(fixedRate = 500)  // Запуск раз в 6 часов
    @Transactional
    public void generateRecommendations() {
        List<Long> userIds = interactionRepository.findAll().stream().map(UserInteraction::getUserId).distinct().toList();
        List<Long> newsIds = interactionRepository.findAll().stream().map(UserInteraction::getNewsId).distinct().toList();

        int userCount = userIds.size();
        int newsCount = newsIds.size();

        Map<Long, Integer> userIndexMap = new HashMap<>();
        Map<Long, Integer> newsIndexMap = new HashMap<>();
        for (int i = 0; i < userCount; i++) {
            userIndexMap.put(userIds.get(i), i);
        }
        for (int i = 0; i < newsCount; i++) {
            newsIndexMap.put(newsIds.get(i), i);
        }

        RealMatrix interactionMatrix = new Array2DRowRealMatrix(userCount, newsCount);

        interactionRepository.findAll().forEach(interaction -> {
            int userIdx = userIndexMap.get(interaction.getUserId());
            int newsIdx = newsIndexMap.get(interaction.getNewsId());
            double interactionValue = getInteractionScore(interaction.getAction());
            interactionMatrix.setEntry(userIdx, newsIdx, interactionValue);
        });

        SingularValueDecomposition svd = new SingularValueDecomposition(interactionMatrix);
        RealMatrix userFeatures = svd.getU();
        RealMatrix newsFeatures = svd.getV().transpose();
        RealMatrix predictedMatrix = userFeatures.multiply(svd.getS()).multiply(newsFeatures);

        List<UserRecommendation> recommendations = new ArrayList<>();

        for (int i = 0; i < userCount; i++) {
            long userId = userIds.get(i);
            for (int j = 0; j < newsCount; j++) {
                long newsId = newsIds.get(j);
                double score = predictedMatrix.getEntry(i, j);
                if (Math.abs(score) < 0.01) {
                    score = 0;
                }
                if (score > 0.99) {
                    score = 1;
                }
                if (score < -0.99) {
                    score = -1;
                }
                recommendations.add(new UserRecommendation(new UserRecommendationId(userId, newsId), score));
            }
        }

        recommendationRepository.deleteAll();
        recommendationRepository.saveAll(recommendations);
    }

    private double getInteractionScore(ActionType interactionType) {
        return switch (interactionType) {
            case LIKE -> 1.0;
            case DISLIKE -> -1.0;
            case VIEW -> 0.5;
        };
    }
}