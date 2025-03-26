-- liquibase formatted sql

-- changeset PanchenkoVA:TST
INSERT INTO sources (name, url, type) VALUES
    ('BBC News', 'https://www.bbc.com/news', 'news'),
    ('CNN', 'https://www.cnn.com', 'news'),
    ('TechCrunch', 'https://techcrunch.com', 'technology'),
    ('ESPN', 'https://www.espn.com', 'sports'),
    ('Hacker News', 'https://news.ycombinator.com', 'technology');


-- ===================================
-- 1) Пользователи (id: 1..12)
-- ===================================
INSERT INTO users (id, username, password_hash) VALUES
    (2, 'User 2', ''),
    (3, 'User 3', ''),
    (4, 'User 4', ''),
    (5, 'User 5', ''),
    (6, 'User 6', ''),
    (7, 'User 7', ''),
    (8, 'User 8', ''),
    (9, 'User 9', ''),
    (10, 'User 10', ''),
    (11, 'User 11', ''),
    (12, 'User 12', '');

-- ===================================
-- 2) Новости (id: 1..12)
-- ===================================
INSERT INTO news_items (id, title, content, link, published_date, source_id) VALUES
    (1, 'Tech News 1', 'Technology topic', 'https://tech.com/news1', '2024-03-01 10:00:00', 1),
    (2, 'Tech News 2', 'Technology topic', 'https://tech.com/news2', '2024-03-01 10:00:00', 1),
    (3, 'Tech News 3', 'Technology topic', 'https://tech.com/news3', '2024-03-01 10:00:00', 1),
    (4, 'Sports News 1', 'Sports topic', 'https://sports.com/news1', '2024-03-01 10:00:00', 2),
    (5, 'Sports News 2', 'Sports topic', 'https://sports.com/news2', '2024-03-01 10:00:00', 2),
    (6, 'Sports News 3', 'Sports topic', 'https://sports.com/news3', '2024-03-01 10:00:00', 2),
    (7, 'Politics News 1', 'Politics topic', 'https://politics.com/news1', '2024-03-01 10:00:00', 3),
    (8, 'Politics News 2', 'Politics topic', 'https://politics.com/news2', '2024-03-01 10:00:00', 3),
    (9, 'Politics News 3', 'Politics topic', 'https://politics.com/news3', '2024-03-01 10:00:00', 3),
    (10, 'Random News 1', 'General topic', 'https://random.com/news1', '2024-03-01 10:00:00', 4),
    (11, 'Random News 2', 'General topic', 'https://random.com/news2', '2024-03-01 10:00:00', 4),
    (12, 'Random News 3', 'General topic', 'https://random.com/news3', '2024-03-01 10:00:00', 4);

-- ===================================
-- 3) Взаимодействия user_news_interactions
--    LIKE => 1, DISLIKE => -1, VIEW => 0.5
--    отсутствие записи => пропуск (0)
-- ===================================

-- Группа 1 (Users 1-4) - Технологии
INSERT INTO user_news_interactions (user_id, news_id, action, timestamp) VALUES
    (1, 1, 'LIKE', '2024-03-01 10:00:00'),
    (1, 2, 'LIKE', '2024-03-01 10:01:00'),
    (1, 3, 'VIEW', '2024-03-01 10:02:00'),
    (2, 1, 'LIKE', '2024-03-01 10:03:00'),
    (2, 2, 'LIKE', '2024-03-01 10:04:00'),
    (3, 3, 'LIKE', '2024-03-01 10:05:00'),
    (4, 1, 'LIKE', '2024-03-01 10:06:00'),
    (4, 2, 'LIKE', '2024-03-01 10:07:00');

-- Группа 2 (Users 5-8) - Спорт
INSERT INTO user_news_interactions (user_id, news_id, action, timestamp) VALUES
    (5, 4, 'LIKE', '2024-03-01 11:00:00'),
    (5, 5, 'LIKE', '2024-03-01 11:01:00'),
    (5, 6, 'VIEW', '2024-03-01 11:02:00'),
    (6, 4, 'LIKE', '2024-03-01 11:03:00'),
    (6, 5, 'LIKE', '2024-03-01 11:04:00'),
    (7, 6, 'LIKE', '2024-03-01 11:05:00'),
    (8, 4, 'LIKE', '2024-03-01 11:06:00'),
    (8, 5, 'LIKE', '2024-03-01 11:07:00');

-- Группа 3 (Users 9-12) - Политика
INSERT INTO user_news_interactions (user_id, news_id, action, timestamp) VALUES
    (9, 7, 'LIKE', '2024-03-01 12:00:00'),
    (9, 8, 'LIKE', '2024-03-01 12:01:00'),
    (9, 9, 'VIEW', '2024-03-01 12:02:00'),
    (10, 7, 'LIKE', '2024-03-01 12:03:00'),
    (10, 8, 'LIKE', '2024-03-01 12:04:00'),
    (11, 9, 'LIKE', '2024-03-01 12:05:00'),
    (12, 7, 'LIKE', '2024-03-01 12:06:00'),
    (12, 8, 'LIKE', '2024-03-01 12:07:00');

