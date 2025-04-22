package com.business_website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.business_website.dto.NewsUpdatesDto;
import com.business_website.models.NewsUpdates;

@Repository
public interface NewsUpdatesRepo extends JpaRepository<NewsUpdates, Long> {
    
    @Query(value = "SELECT * FROM newsandupdates ORDER BY created_at DESC", nativeQuery = true)
    List<NewsUpdates> findAllByOrderByCreatedDateDesc();

    NewsUpdates save(NewsUpdatesDto newsUpdatesDto);
}
