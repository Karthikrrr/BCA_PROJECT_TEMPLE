package com.business_website.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.business_website.dto.NewsUpdatesDto;
import com.business_website.models.NewsUpdates;

public interface NewsUpdatesService {
    NewsUpdates save(NewsUpdatesDto newsUpdatesDto, MultipartFile file);
    List<NewsUpdates> getAllNewsUpdates();
    NewsUpdates getNewsUpdatesById(Long id);
    NewsUpdates updateNewsUpdates(NewsUpdatesDto newsUpdatesDto, Long id, MultipartFile file);
    void deleteNewsUpdate(Long id);
}
