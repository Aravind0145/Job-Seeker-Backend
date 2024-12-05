package com.Aravind.demo.Quries;

public class JobSeekerQueries {

    public static final String GET_ROLE_BY_EMAIL_AND_PASSWORD =
            "SELECT j.role FROM JobSeeker j WHERE j.email = :email AND j.password = :password";

    public static final String  GET_ID_BY_EMAIL_AND_PASSWORD=
            "SELECT j.id FROM JobSeeker j WHERE j.email = :email AND j.password = :password";


   public static final String GET_FULLNAME_BY_EMAIL_AND_PASSWORD =
           "SELECT j.fullName FROM JobSeeker j WHERE j.email = :email AND j.password = :password";


   public static final String GET_RESUME_BY_JOBSEEKERID=
           "FROM Resume r WHERE r.jobSeeker.id = :jobseekerId"; // Use jobSeeker.id to reference the JobSeeker's id


    public static final String UPDATE_JOBSEEKER_PASSWORD =
            "UPDATE JobSeeker j SET j.password = :password WHERE j.email = :email";

    public static final String CHECK_EMAIL =
            "SELECT COUNT(j) FROM JobSeeker j WHERE j.email = :email";

 public static final String UPDATE_JOBSEEKER = "UPDATE JobSeeker j " +
            "SET j.fullName = :fullName, " +
            "j.email = :email, " +
            "j.password = :password, " +
            "j.phone = :phone, " +
            "j.workStatus = :workStatus, " +
            "j.promotions = :promotions " +
            "WHERE j.id = :id";

  public static final String GET_APPLICATION_BY_JOBSEEKER =
          "FROM Applications a WHERE a.jobSeeker.id = :jobSeekerId"; // HQL query

    public static final String WITHDRAW_APPLICATION =
            "FROM Applications WHERE applicationId = :applicationId AND jobSeeker.id = :jobSeekerId";

   public static final  String SEARCH_JOBS = "FROM JobPosting j WHERE " +
            "(j.jobTitle LIKE :jobTitle OR :jobTitle IS NULL) AND " +
            "(j.location LIKE :location OR :location IS NULL) AND " +
            "(j.experience LIKE :experience OR :experience IS NULL)";

   public static final String UPDATE_RESUME = "UPDATE Resume r " +
            "SET r.headline = :headline, " +
            "r.firstName = :firstName, " +
            "r.middleName = :middleName, " +
            "r.lastName = :lastName, " +
            "r.email = :email, " +
            "r.phoneNumber = :phoneNumber, " +
            "r.dob = :dob, " +
            "r.languages = :languages, " +
            "r.linkedinurl = :linkedinurl, " +
            "r.permanentAddress = :permanentAddress, " +
            "r.currentAddress = :currentAddress, " +
            "r.xth = :xth, " +
            "r.xthYear = :xthYear, " +
            "r.xii = :xii, " +
            "r.xiiYear = :xiiYear, " +
            "r.graduation = :graduation, " +
            "r.graduationYear = :graduationYear, " +
            "r.pg = :pg, " +
            "r.pgStatus = :pgStatus, " +
           "r.skills =:skills, "+
            "r.projectTitle = :projectTitle, " +
            "r.projectDescription = :projectDescription, " +
            "r.certificateName = :certificateName, " +
            "r.certificateDescription = :certificateDescription, " +
            "r.companyName = :companyName, " +
            "r.startDate = :startDate, " +
            "r.endDate = :endDate, " +
            "r.jobTitle = :jobTitle, " +
            "r.jobDescription = :jobDescription " +
            "WHERE r.id = :id";
}
