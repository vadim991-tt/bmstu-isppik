   package bmstu.isppik.isppik_server.service;

import bmstu.isppik.isppik_server.model.NewsItem;
import bmstu.isppik.isppik_server.model.Source;
import bmstu.isppik.isppik_server.repository.NewsRepository;
import bmstu.isppik.isppik_server.repository.SourceRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsFetchingService {

    private final SourceRepository sourceRepository;
    private final NewsRepository newsRepository;

    // Периодическая загрузка новостей (каждый час)
    @Scheduled(fixedRate = 3600000)  // Каждые 60 минут
    public void fetchNews() {
        List<Source> sources = sourceRepository.findAll();  // Получаем все источники
        for (Source source : sources) {
            List<NewsItem> newsItems = fetchNewsFromSource(source);  // Загружаем новости с каждого источника
            saveNews(newsItems);  // Сохраняем загруженные новости в базу
        }
    }

    // Логика загрузки новостей из конкретного источника (например, через RSS)
    private List<NewsItem> fetchNewsFromSource(Source source) {
        List<NewsItem> newsItems = new ArrayList<>();
        try {
            URL feedUrl = new URL(source.getUrl());
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            // Парсим каждую новость из RSS-ленты
            for (SyndEntry entry : feed.getEntries()) {
                NewsItem newsItem = new NewsItem();
                newsItem.setTitle(entry.getTitle());
                newsItem.setContent(entry.getDescription() != null ? entry.getDescription().getValue() : "");
                newsItem.setLink(entry.getLink());
                newsItem.setPublishedDate(
                    entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                );
                newsItem.setSourceId(source.getId());
                newsItems.add(newsItem);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Логируем ошибки при парсинге RSS
        }
        return newsItems;
    }

    // Сохранение новостей в базу данных
    private void saveNews(List<NewsItem> newsItems) {
        for (NewsItem item : newsItems) {
            // Проверка, существует ли уже такая новость по уникальной ссылке, чтобы избежать дублирования
            if (!newsRepository.existsByLink(item.getLink())) {
                newsRepository.save(item);
            }
        }
    }
}
