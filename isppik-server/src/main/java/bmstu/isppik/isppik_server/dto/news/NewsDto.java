package com.example.newsapp.dto;

import com.example.newsapp.model.NewsItem;

import java.time.LocalDateTime;

@Data
public class NewsDto {
    private Long id;
    private String title;
    private String content;
    private String link;
    private LocalDateTime publishedDate;
    private String sourceName;

    public NewsDto(NewsItem newsItem) {
        this.id = newsItem.getId();
        this.title = newsItem.getTitle();
        this.content = newsItem.getContent();
        this.link = newsItem.getLink();
        this.publishedDate = newsItem.getPublishedDate();
        this.sourceName = newsItem.getSource().getName();
    }

    // Геттеры и сеттеры
}
