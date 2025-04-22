package com.business_website.service_implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.business_website.dto.NewsUpdatesDto;
import com.business_website.models.NewsUpdates;
import com.business_website.repository.NewsUpdatesRepo;
import com.business_website.services.NewsUpdatesService;

@Service
public class NewsUpdatesServiceImplementation implements NewsUpdatesService {

    @Autowired
    private NewsUpdatesRepo newsUpdatesRepo;

    @Autowired 
    private FileUploadServiceImplementation fileUploadServiceImplementation;

    @Override
    public NewsUpdates save(NewsUpdatesDto newsUpdatesDto, MultipartFile file) {
        newsUpdatesDto.setCreatedAt(LocalDateTime.now());
        if(!file.isEmpty()){
            String imageUrl = fileUploadServiceImplementation.saveFile(file);
            newsUpdatesDto.setImageUrl(imageUrl);
        }
        NewsUpdates newsUpdates = new NewsUpdates(
                                newsUpdatesDto.getContent(),
                                newsUpdatesDto.getCreatedAt(),
                                newsUpdatesDto.getImageUrl(),
                                newsUpdatesDto.getNews());

        return newsUpdatesRepo.save(newsUpdates);
    }

    @Override
    public List<NewsUpdates> getAllNewsUpdates() {
        return newsUpdatesRepo.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public NewsUpdates getNewsUpdatesById(Long id) {
        NewsUpdates newsUpdates = newsUpdatesRepo.findById(id).get();
        return newsUpdates;
    }

    @Override
    public NewsUpdates updateNewsUpdates(NewsUpdatesDto newsUpdatesDto, Long id, MultipartFile file) {
        NewsUpdates existingNewsUpdates = getNewsUpdatesById(id);
        existingNewsUpdates.setContent(newsUpdatesDto.getContent());
        existingNewsUpdates.setNews(newsUpdatesDto.getNews());
        
        if(!file.isEmpty()){
            String imageUrl = fileUploadServiceImplementation.saveFile(file);
            existingNewsUpdates.setImageUrl(imageUrl);
        }
        return newsUpdatesRepo.save(existingNewsUpdates);
    }

    @Override
    public void deleteNewsUpdate(Long id) {
        NewsUpdates newsUpdates = getNewsUpdatesById(id);
        newsUpdatesRepo.delete(newsUpdates);
    }

}
