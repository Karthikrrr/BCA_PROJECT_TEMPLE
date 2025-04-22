package com.business_website.service_implementation;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.business_website.dto.ProjectsDto;
import com.business_website.models.Temple;
import com.business_website.repository.TempleRepo;
import com.business_website.services.TempleService;

@Service
public class TempleServiceImplementation implements TempleService {

    @Autowired
    private TempleRepo projectsRepo;

    @Autowired
    private FileUploadServiceImplementation fileUploadServiceImplementation;

    @Override
    public Temple save(ProjectsDto templeDto, MultipartFile file) {
        templeDto.setCreatedAt(LocalDateTime.now());
        if (!file.isEmpty()) {
            String imageUrl = fileUploadServiceImplementation.saveFile(file);
            System.out.println(imageUrl);
            templeDto.setImageUrl(imageUrl);
        }
        Temple temple = new Temple();
        temple.setTitle(templeDto.getTitle());
        temple.setContent(templeDto.getContent());
        temple.setCreatedAt(templeDto.getCreatedAt());
        temple.setArchanaAmount(templeDto.getArchanaAmount());
        temple.setImageUrl(templeDto.getImageUrl());
        temple.setArthiAmount(templeDto.getArthiAmount());
        temple.setPrsadamAmount(templeDto.getPrsadamAmount());
        temple.setSpecialPoojaAmount(templeDto.getSpecialPoojaAmount());
                
        return projectsRepo.save(temple);
    }


    @Override
    public List<Temple> getAllProjects() {
        return projectsRepo.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public Temple getProjectById(Long id) {
        Temple project = projectsRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        return project;
    }

    @Override
    public Temple updateProject(ProjectsDto projectDto, Long id, MultipartFile file) {
        Temple existingpProject = getProjectById(id);
        existingpProject.setTitle(projectDto.getTitle());
        existingpProject.setContent(projectDto.getContent());

        if (!file.isEmpty()) {
            String imageUrl = fileUploadServiceImplementation.saveFile(file);
            existingpProject.setImageUrl(imageUrl);
        }

        return projectsRepo.save(existingpProject);
    }

    @Override
    public void deleteProject(Long id) {
        Temple project = getProjectById(id);
        projectsRepo.delete(project);
    }

}
