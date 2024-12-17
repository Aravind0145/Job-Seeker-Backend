package com.Aravind.demo.entity;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobseeker")
public class JobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(nullable = false)
    private String workStatus;

    @Column(nullable = false)
    private boolean promotions;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'jobseeker'")
    private String role;




    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationTime;

    public JobSeeker() {
        this.role = "jobseeker";

    }

    public JobSeeker(String fullName, String email, String password,String profilePhoto, String phone, String workStatus, boolean promotions) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.profilePhoto = profilePhoto;
        this.workStatus = workStatus;
        this.promotions = promotions;
        this.role = "jobseeker";
        // No need to manually set registrationTime here
    }

    // Use @PrePersist to automatically set registrationTime before persisting
    @PrePersist
    public void prePersist() {
        if (this.registrationTime == null) {
            this.registrationTime = LocalDateTime.now();
        }
    }

    // Getter and Setter methods

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public boolean isPromotions() {
        return promotions;
    }

    public void setPromotions(boolean promotions) {
        this.promotions = promotions;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }
}
