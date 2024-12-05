package com.Aravind.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Table(name = "job_postings")
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    // One JobPosting belongs to one Employee
    @ManyToOne(cascade = CascadeType.ALL) // Add CascadeType.ALL
    @JoinColumn(name = "employee_id", nullable = false)  // Foreign key column in JobPosting
    private Employee employee;  // This represents the Employee who posted the job

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    private String jobDescription;

    @Column(nullable = false)
    private String rolesAndResponsibilities; // New field for roles and responsibilities

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String employmentType;

    @Column(nullable = false)
    private String salary;

    @Column(nullable = false)
    private String jobCategory;

    @Column(nullable = false)
    private String skills;

    @Column(nullable = false)
    private String experience;

    @Column(nullable = false)
    private String education; // New field for education

    @Column(nullable = false)
    private int numberOfOpenings; // New field for number of openings

    @CreationTimestamp
    @Column(nullable = false)
    private Date postedDate;

    @Column(nullable = false)
    private Date lastDate;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getRolesAndResponsibilities() {
        return rolesAndResponsibilities;
    }

    public void setRolesAndResponsibilities(String rolesAndResponsibilities) {
        this.rolesAndResponsibilities = rolesAndResponsibilities;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getNumberOfOpenings() {
        return numberOfOpenings;
    }

    public void setNumberOfOpenings(int numberOfOpenings) {
        this.numberOfOpenings = numberOfOpenings;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }
}
