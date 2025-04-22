package com.business_website.dto;

import java.time.LocalDateTime;

public class ProjectsDto {

    private String title;
    private String content;
    private String imageUrl;
    private int arthiAmount;
    private int archanaAmount;
    private int prsadamAmount;
    private int specialPoojaAmount;
    private LocalDateTime createdAt;


    

    public int getArthiAmount() {
        return arthiAmount;
    }

    public void setArthiAmount(int arthiAmount) {
        this.arthiAmount = arthiAmount;
    }

    public int getArchanaAmount() {
        return archanaAmount;
    }

    public void setArchanaAmount(int archanaAmount) {
        this.archanaAmount = archanaAmount;
    }

    public int getPrsadamAmount() {
        return prsadamAmount;
    }

    public void setPrsadamAmount(int prsadamAmount) {
        this.prsadamAmount = prsadamAmount;
    }

    public int getSpecialPoojaAmount() {
        return specialPoojaAmount;
    }

    public void setSpecialPoojaAmount(int specialPoojaAmount) {
        this.specialPoojaAmount = specialPoojaAmount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
