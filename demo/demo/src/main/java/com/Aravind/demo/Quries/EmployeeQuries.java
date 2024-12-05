package com.Aravind.demo.Quries;

public class EmployeeQuries {

    public static final String getRoleByEmailAndPassword =
             "SELECT e.role FROM Employee e WHERE e.email = :email AND e.password = :password";

    public static final String getIdByEmailAndPassword=
            "SELECT e.id FROM Employee e WHERE e.email = :email AND e.password = :password";

  public static final String getFullNameByEmailAndPassowrd =
          "SELECT e.fullName FROM Employee e WHERE e.email = :email AND e.password = :password";

  public static final String UpdateEmployeePassword
          = "UPDATE Employee e SET e.password = :password WHERE e.email = :email";


  public static final String isEmailExits =
          "SELECT COUNT(e) FROM Employee e WHERE e.email = :email";

  public static final String getallJobPostings
           = "FROM JobPosting";  // HQL query to select all records from JobPosting entity

    public static final String UpdateEmployee=
             "UPDATE Employee e " +
            "SET e.companyName = :companyName, " +
            "e.websiteUrl = :websiteUrl, " +
            "e.industryType = :industryType, " +
            "e.fullName = :fullName, " +
            "e.email = :email, " +
            "e.mobileNumber = :mobileNumber, " +
            "e.designation = :designation, " +
            "e.password = :password, " +
            "e.role = :role " +
            "WHERE e.id = :id";

    public static final String getallJobPostingsByJobseekerid =
    "FROM JobPosting jp WHERE jp.employee.id = :jobSeekerId";

    public static final String getcountApplications=
            "SELECT COUNT(DISTINCT a.jobSeeker) FROM Applications a WHERE a.jobPosting.id = :jobPostingId";

    public static final String findresumebyjobjobpostingid = "SELECT a.resume FROM Applications a " +
            "JOIN a.jobPosting jp " +
            "WHERE jp.id = :jobPostingId";

    public static final String getApplicationById =
            "FROM Applications a " +
                    "JOIN FETCH a.jobPosting " +
                    "JOIN FETCH a.jobSeeker " +
                    "JOIN FETCH a.resume " +
                    "WHERE a.jobPosting.id = :jobPostingId";
    public static final String UpdateApplication= "UPDATE Applications a " +
            "SET a.jobPosting = :jobPosting, " +
            "a.jobSeeker = :jobSeeker, " +
            "a.resume = :resume, " +
            "a.status = :status " +
            "WHERE a.applicationId = :applicationId"; // Filter by applicationId


  public static final String DleteapplicationId =
          "DELETE FROM Applications a WHERE a.jobPosting.id = :jobPostingId";

  public static final String DeleteJobposting =
          "DELETE FROM JobPosting jp WHERE jp.id = :jobPostingId";

  public static final String submitapplication=
          "FROM Applications WHERE jobSeeker.id = :jobSeekerId AND jobPosting.id = :jobPostingId";





}
