package com.Aravind.demo.Constants;

public class EmployeeURLConstants {

    public static final String EMPLOYEE_REGISTER = "/employee/empregister";

    public static final String EMPLOYEE_LOGIN ="/employee/emplogin";

    public static final String EMPLOYEE_UPDATEPASSSORD="/employee/empforgotpassword";

    public static final String EMPLOYEE_CHECKMAIL = "/employee/updateemail";

    public static final String EMPLOYEE_JOBPOSTINGS="/employee/postjobs/{id}";

    public static final String EMPLOYEE_LISTOFJOBPOSTINGS="/jobseeker/listjobpostings/{page}/{size}";

    public static final String EMPLOYEE_VIEWPROFILE= "/employee/empprofile/{id}";

    public static final String EMPLOYEE_UPDATEPROFILE = "/employee/updateemployeeprofile/{id}";

    public static final String EMPLOYEE_VIEWJOBPOSTINGS="/employee/viewjobpostings/{id}";

    public static final String EMPLOYEE_COUNTJOBS="/employee/appliedcounts/{jobPostingId}";


    public static final String EMPLOYEE_RESUMEDEATILS = "/employee/resumedetails/{jobPostingId}";

    public static final String EMPLOYEE_APPLICATION ="/employee/applicationdetails/{jobPostingId}";

    public static final String EMPLOYEE_UPDATEAPPLICATION = "/employee/updateapplication/{id}";

    public static final String SHORTLISTED ="/applications/{jobPostingId}/{jobSeekerId}/{applicationId}";

    public static final String DeleteJobposting = "/DeleteJobposting/{id}" ;
}
