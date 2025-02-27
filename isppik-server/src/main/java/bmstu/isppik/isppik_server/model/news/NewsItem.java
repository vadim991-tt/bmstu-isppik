package bmstu.isppik.isppik_server.model.news;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news_items")
@Data
public class NewsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String link;

    private LocalDateTime publishedDate;

    private Long sourceId;
}
