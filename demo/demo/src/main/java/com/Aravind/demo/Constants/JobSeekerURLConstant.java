package com.Aravind.demo.Constants;

import com.Aravind.demo.entity.JobSeeker;

import java.awt.*;

public class JobSeekerURLConstant {

    public static final String JOBSEEKER_REGISTER = "/jobseeker/jregister";

    public static final String JOBSSEEKER_LOGIN = "/jobseeker/jfrontpage";


    public static final String JOBSEEKER_RESUME = "/jobseeker/jobseekerresume/{id}";

    public static final String JOBSEEKER_CHECK_RESUME = "/jobseeker/jobseekerresume/check/{jobseekerId}";


    public static final String JOBSEEKER_RESUMEDETAILS="/jobseeker/jobseekerhomepage/{id}";


    public static final String JOBSEEKER_UPDATEPASSWORD = "/jobseeker/jobseekerforgotpassword";


    public static final String JOBSEEKER_CEHCKEMAIL = "/jobseeker/jfrontapge";


    public static final String JOBSEEKER_APPLICATIONS="/jobseeker/jobdetails/{jobPostingId}/{jobSeekerId}/{resumeId}";

    public static final String  JOBSEEKER_VIEWPROFILE="/jobseeker/viewprofile/{id}";


    public static  final String JOBSEEKR_UPDATEPROFILE="/jobseeker/updateprofile/{id}";

    public static final String JOBSEEKER_APPLIEDJOBS = "/jobseeker/applyjobs/{jobSeekerId}";


    public static  final String JOBSEEKER_WITHDRAW="/jobseeker/{jobSeekerId}/applications/{applicationId}";


    public static final String JOBSEEKER_SEARCHJOB = "/jobseeker/searchjob/{jobTitle}/{location}/{experience}";


    public static final String JOBSEEEKR_VIEWRESUME="/jobseeker/viewresume/{id}";


    public static final String JOBSEEKER_UPDATERESUME="/jobseeker/updateresume/{id}";
}
