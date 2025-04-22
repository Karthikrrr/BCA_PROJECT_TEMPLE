package com.business_website.dto;

import java.time.LocalDateTime;

public class NewsUpdatesDto {

    private String news;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }


}
