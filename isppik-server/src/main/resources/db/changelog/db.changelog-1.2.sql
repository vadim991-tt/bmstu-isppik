-- src/main/resources/db/changelog/db.changelog-1.2.sql

-- liquibase formatted sql

-- changeset PanchenkoVA:InsertTestSources
INSERT INTO sources (name, url, type) VALUES
    ('BBC News', 'https://www.bbc.com/news', 'news'),
    ('CNN', 'https://www.cnn.com', 'news'),
    ('TechCrunch', 'https://techcrunch.com', 'technology'),
    ('ESPN', 'https://www.espn.com', 'sports'),
    ('Hacker News', 'https://news.ycombinator.com', 'technology');

-- changeset PanchenkoVA:InsertTestNewsItems
INSERT INTO news_items (title, content, link, published_date, source_id) VALUES
    ('BBC: Политические события', 'Актуальные события в мире политики...', 'https://www.bbc.com/news/politics', '2024-03-01 10:00:00', 1),
    ('CNN: Экономика США', 'Новые тренды в экономике США...', 'https://www.cnn.com/news/economy', '2024-03-02 12:30:00', 2),
    ('TechCrunch: ИИ захватывает рынок', 'Инновации в области искусственного интеллекта...', 'https://techcrunch.com/ai', '2024-03-03 14:15:00', 3),
    ('ESPN: Новости футбола', 'Обзор матчей Лиги чемпионов...', 'https://www.espn.com/soccer', '2024-03-04 16:45:00', 4),
    ('Hacker News: Новая технология', 'Обзор последнего обновления языка программирования...', 'https://news.ycombinator.com/item?id=123', '2024-03-05 08:00:00', 5);

-- changeset PanchenkoVA:InsertTestSubscriptions
INSERT INTO subscriptions (user_id, source_id) VALUES
    (1, 1), -- Админ подписан на BBC
    (1, 3), -- Админ подписан на TechCrunch
    (1, 5); -- Админ подписан на Hacker News
