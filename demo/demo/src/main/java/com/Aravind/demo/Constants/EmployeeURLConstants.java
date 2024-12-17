package com.Aravind.demo.Constants;

public class EmployeeURLConstants {


    public static final String FRONTEND_URL = "http://localhost:4200";

    public static final String BASE_API_URL = "/api";

    public static final String EMPLOYEE_REGISTER = "/employee/register";

    public static final String EMPLOYEE_LOGIN ="/employee/login";

    public static final String EMPLOYEE_UPDATEPASSSORD="/employee/forgot-password";

    public static final String EMPLOYEE_CHECKMAIL = "/employee/update-email";

    public static final String EMPLOYEE_JOBPOSTINGS="/employee/post-jobs/{id}";

    public static final String EMPLOYEE_LISTOFJOBPOSTINGS="/jobseeker/list-jobpostings/{page}/{size}";

    public static final String EMPLOYEE_VIEWPROFILE= "/employee/emp-profile/{id}";

    public static final String EMPLOYEE_UPDATEPROFILE = "/employee/update-employee-profile/{id}";

    public static final String EMPLOYEE_VIEWJOBPOSTINGS="/employee/view-jobpostings/{id}";

    public static final String EMPLOYEE_COUNTJOBS="/employee/applied-counts/{jobPostingId}";


    public static final String EMPLOYEE_RESUMEDEATILS = "/employee/resume-details/{jobPostingId}";

    public static final String EMPLOYEE_APPLICATION ="/employee/application-details/{jobPostingId}";

    public static final String EMPLOYEE_UPDATEAPPLICATION = "/employee/update-application/{id}";

    public static final String SHORTLISTED ="/applications/{jobPostingId}/{jobSeekerId}/{applicationId}";

    public static final String DeleteJobposting = "/Delete-Jobposting/{id}" ;
}
