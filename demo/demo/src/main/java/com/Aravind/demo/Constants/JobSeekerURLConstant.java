package com.Aravind.demo.Constants;



public class JobSeekerURLConstant {

    public static final String FRONTEND_URL = "http://localhost:4200";

    public static final String BASE_API_URL = "/api";


    public static final String JOBSEEKER_REGISTER = "/jobseeker/register";

    public static final String JOBSSEEKER_LOGIN = "/jobseeker/frontpage";


    public static final String JOBSEEKER_RESUME = "/jobseeker/resume/{id}";

    public static final String JOBSEEKER_CHECK_RESUME = "/jobseeker/resume/check/{jobseekerId}";


    public static final String JOBSEEKER_RESUMEDETAILS="/jobseeker/homepage/{id}";


    public static final String JOBSEEKER_UPDATEPASSWORD = "/jobseeker/forgot-password";


    public static final String JOBSEEKER_CEHCKEMAIL = "/jobseeker/update-emails";


    public static final String JOBSEEKER_APPLICATIONS="/jobseeker/job-details/{jobPostingId}/{jobSeekerId}/{resumeId}";

    public static final String  JOBSEEKER_VIEWPROFILE="/jobseeker/view-profile/{id}";


    public static  final String JOBSEEKR_UPDATEPROFILE="/jobseeker/update-profile/{id}";

    public static final String JOBSEEKER_APPLIEDJOBS = "/jobseeker/applyjobs/{jobSeekerId}";


    public static  final String JOBSEEKER_WITHDRAW="/jobseeker/{jobSeekerId}/applications/{applicationId}";


    public static final String JOBSEEKER_SEARCHJOB = "/jobseeker/search-job/{jobTitle}/{location}/{experience}";


    public static final String JOBSEEEKR_VIEWRESUME="/jobseeker/view-resume/{id}";


    public static final String JOBSEEKER_UPDATERESUME="/jobseeker/update-resume/{id}";


    public static final String APPLICATION_STATUS = "/applications/status";
}
