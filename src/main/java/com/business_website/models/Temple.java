package com.business_website.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Temple {
    

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="content", length = 10000)
    private String content;

    @Column(name="imageurl")
    private String imageUrl;

    private int arthiAmount;

    private int archanaAmount;

    private int prsadamAmount;

    private int specialPoojaAmount;

    @Column(name="createdAt")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public Temple(){
        super();
    }



    public Temple(String title, String content, String imageUrl, int arthiAmount, int archanaAmount, int prsadamAmount,
            int specialPoojaAmount, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.arthiAmount = arthiAmount;
        this.archanaAmount = archanaAmount;
        this.prsadamAmount = prsadamAmount;
        this.specialPoojaAmount = specialPoojaAmount;
        this.createdAt = createdAt;
    }



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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
