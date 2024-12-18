package com.Aravind.demo.entity;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String companyName;
    private String websiteUrl;


    private String fullName;
    private String email;
    private String mobileNumber;

    @Column(name = "profile_photo")
    private String profilePhoto;
    private String designation;


    private String password;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'employee'")
    private String role;


    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    public Employee() {
        this.role = "employee";
    }


    public Employee(String companyName, String websiteUrl, String industryType,
                    String fullName, String officialEmail, String mobileNumber,
                    String designation, String password) {
        this.companyName = companyName;
        this.websiteUrl = websiteUrl;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.designation = designation;
        this.password = password;
        this.role = "employee"; // Default role
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOfficialEmail() {
        return email;
    }

    public void setOfficialEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPassword() {
        return password;
    }
    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
