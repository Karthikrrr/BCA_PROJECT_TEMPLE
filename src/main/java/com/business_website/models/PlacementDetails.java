package com.business_website.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usersdetails")
public class PlacementDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name="phone")
    private String phoneNo;

    @Column(name="qualification")
    private String qualification;


    @Column(name="organizationworked")
    private String organizationWorked;

    @Column(name="experienced")
    private String experience;

    @Column(name="resume")
    private String resume;

    @Column(name="createdAt")
    private LocalDateTime createdAt;

    public PlacementDetails(){
        super();
    }
   

    public PlacementDetails(LocalDateTime createdAt, String email, String experience, String name, String organizationWorked, String phoneNo, String qualification, String resume) {
        this.createdAt = createdAt;
        this.email = email;
        this.experience = experience;
        this.name = name;
        this.organizationWorked = organizationWorked;
        this.phoneNo = phoneNo;
        this.qualification = qualification;
        this.resume = resume;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getOrganizationWorked() {
        return organizationWorked;
    }

    public void setOrganizationWorked(String organizationWorked) {
        this.organizationWorked = organizationWorked;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
