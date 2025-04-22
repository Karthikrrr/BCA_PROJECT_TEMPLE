package com.business_website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.business_website.dto.ProjectsDto;
import com.business_website.models.Temple;

@Repository
public interface TempleRepo extends JpaRepository<Temple, Long>{
    
    @Query(value = "SELECT * FROM temple ORDER BY created_at DESC", nativeQuery = true)
    List<Temple> findAllByOrderByCreatedDateDesc();
    
    Temple save(ProjectsDto projectsDto);
}
