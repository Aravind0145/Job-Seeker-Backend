package com.Aravind.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Applications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private long applicationId;

    @ManyToOne
    @JoinColumn(name = "job_postings_id", nullable = false)
    private JobPosting jobPosting; // One job posting can have many applications

    @ManyToOne
    @JoinColumn(name = "jobseeker_id", nullable = false)
    private JobSeeker jobSeeker; // One job seeker can apply for many jobs

    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume; // One resume can be used for many applications*/

    @Column(name = "status", nullable = false, length = 50)
    private String status = "Pending"; // Default value for status

    @CreationTimestamp // Automatically sets the current timestamp when the record is created
    @Column(name = "applied_at", nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    // Constructors, Getters, Setters, etc.
    public Applications() {
    }

    public Applications(JobPosting jobPosting, JobSeeker jobSeeker, Resume resume, String status) {
        this.jobPosting = jobPosting;
        this.jobSeeker = jobSeeker;
        this.resume = resume;
        this.status = status != null ? status : "Pending"; // Fallback to default if not provided
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status != null ? status : "Pending"; // Ensure default value if null
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}
