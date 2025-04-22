package com.business_website.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.business_website.dto.ProjectsDto;
import com.business_website.models.Temple;

public interface TempleService {
    Temple save(ProjectsDto projectsDto, MultipartFile file);
    List<Temple> getAllProjects();
    Temple getProjectById(Long id);
    Temple updateProject(ProjectsDto projectDto, Long id, MultipartFile file);
    void deleteProject(Long id);
    
}
