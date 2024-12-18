package com.Aravind.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
@Entity
@Table(name="resume")

public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "jobseekerid", nullable = false)
    private JobSeeker jobSeeker;

    @Column(nullable = false)
    private String headline;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String dob;

    @Column(nullable = false)
    private String languages;

    private String linkedinurl;

    @Column(nullable = false)
    private String permanentAddress;

    @Column(nullable = false)
    private String currentAddress;



    private String profilePhoto;

    @Column(nullable = false)
    private String xth;

    @Column(nullable = false)
    private String xthYear;

    @Column(nullable = false)
    private String xii;

    @Column(nullable = false)
    private String xiiYear;

    @Column(nullable = false)
    private String graduation;

    @Column(nullable = false)
    private String graduationYear;

    private String pg;

    private String pgStatus;

    private String skills;

    @Column(nullable = false)
    private String projectTitle;

    @Column(nullable = false)
    private String projectDescription;

    @Column(nullable = false)
    private String certificateName;

    @Column(nullable = false)
    private String certificateDescription;



    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }


    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getLinkedinurl() {
        return linkedinurl;
    }

    public void setLinkedinurl(String linkedinurl) {
        this.linkedinurl = linkedinurl;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getXth() {
        return xth;
    }

    public void setXth(String xth) {
        this.xth = xth;
    }

    public String getXthYear() {
        return xthYear;
    }

    public void setXthYear(String xthYear) {
        this.xthYear = xthYear;
    }

    public String getXii() {
        return xii;
    }

    public void setXii(String xii) {
        this.xii = xii;
    }

    public String getXiiYear() {
        return xiiYear;
    }

    public void setXiiYear(String xiiYear) {
        this.xiiYear = xiiYear;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getPg() {
        return pg;
    }

    public void setPg(String pg) {
        this.pg = pg;
    }

    public String getPgStatus() {
        return pgStatus;
    }

    public void setPgStatus(String pgStatus) {
        this.pgStatus = pgStatus;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertificateDescription() {
        return certificateDescription;
    }

    public void setCertificateDescription(String certificateDescription) {
        this.certificateDescription = certificateDescription;
    }

    public  void setSkills(String skills){
        this.skills=skills;
    }
    public String getSkills(){
        return skills;
    }
}


