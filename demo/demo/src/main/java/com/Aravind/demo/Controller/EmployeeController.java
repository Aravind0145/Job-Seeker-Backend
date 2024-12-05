package com.Aravind.demo.Controller;

import com.Aravind.demo.Constants.EmployeeURLConstants;
import com.Aravind.demo.Constants.JobSeekerURLConstant;
import com.Aravind.demo.Exception.BusinessServiceException;
import com.Aravind.demo.Service.EmailService;
import com.Aravind.demo.Service.EmployeeService;
import com.Aravind.demo.Service.JobService;
import com.Aravind.demo.entity.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.plaf.ViewportUI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);


    @Autowired
    private EmployeeService empService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JobService jobService;

    /**
     * Registers a new employee by saving the employee details and sending a welcome email.
     *
     * @param employee The employee object containing the details to be saved.
     * @return The saved Employee object.
     * @throws BusinessServiceException If there is any error during the employee registration process.
     */
    @PostMapping(EmployeeURLConstants.EMPLOYEE_REGISTER)
    public Employee AddEmployees(@RequestBody Employee employee) throws BusinessServiceException {
        empService.saveEmployees(employee);
        logger.info(employee.getFullName()+"Employee registered successfully");
        emailService.sendEmail(employee.getOfficialEmail(),
                "Welcome to RevHire!"+employee.getFullName(),
                "Thank you for registering with RevHire!");// Save the JobSeeker entity using saveEmployees
        return employee;
    }


    /**
     *
     * Handles the login request for an employee by validating their credentials and retrieving necessary user information.
     *
     * @param loginRequest A map containing the email and password of the employee for authentication.
     * @return A ResponseEntity containing a map with the employee's role, ID, and full name if the login is successful,
     *         or a 401 Unauthorized response if the credentials are invalid.
     * @throws BusinessServiceException If an error occurs while processing the login request.
     */

    @PostMapping(EmployeeURLConstants.EMPLOYEE_LOGIN)
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) throws BusinessServiceException {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        // Assuming you have a method to retrieve the user's role and ID
        String role = empService.getRoleByEmailAndPassword(email, password);
        Long id = empService.getIdByEmailAndPassword(email, password);
        String fullName = empService.getNameByEmailAndPassword(email,password);
        logger.info(fullName+"logged in successfully");
        if (role != null && id != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("role", role);
            response.put("id", id);
            response.put("fullName",fullName);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }

    /**
     * Endpoint to update the password of an employee.
     *
     * @param passwordUpdateRequest A map containing the employee's email and the new password.
     * @return A ResponseEntity containing a message about the success or failure of the password update.
     * @throws BusinessServiceException If there is an error during the password update process in the service layer.
     */
    @PutMapping(EmployeeURLConstants.EMPLOYEE_UPDATEPASSSORD)
    public ResponseEntity<Map<String, Object>> updateEmployeePassword(@RequestBody Map<String, String> passwordUpdateRequest)  throws BusinessServiceException{
        String email = passwordUpdateRequest.get("email");
        String newPassword = passwordUpdateRequest.get("password");

        // Call service to update the password
        boolean isUpdated = empService.updateEmployeePassword(email, newPassword);
        logger.info("Password updated successfully");
        Map<String, Object> response = new HashMap<>();
        if (isUpdated) {
            response.put("message", "Password updated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Employee not found or invalid email");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Checks if an employee exists with the given email.
     *
     * @param email The email address to check for existence in the database.
     * @return {@code true} if an employee with the specified email exists, {@code false} otherwise.
     * @throws BusinessServiceException If there is an error while checking the email existence.
     */

    @GetMapping(EmployeeURLConstants.EMPLOYEE_CHECKMAIL)
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) throws BusinessServiceException {
        boolean exists = empService.isEmailExists(email);
        logger.info("Checking the email if already present");
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }


    /**
     * Adds a job posting for an employee by associating it with the provided employee ID.
     * If the employee exists, the job posting is saved and returned with a 200 OK status.
     * If the employee does not exist, a 404 NOT FOUND status is returned.
     *
     * @param id The ID of the employee to whom the job posting should be associated.
     * @param jobPosting The job posting details to be added for the employee.
     * @return ResponseEntity containing the saved job posting or a 404 status if the employee is not found.
     * @throws BusinessServiceException if there is an issue with fetching the employee or saving the job posting.
     */

    @PostMapping(EmployeeURLConstants.EMPLOYEE_JOBPOSTINGS)
    public ResponseEntity<JobPosting> addJobPosting(@PathVariable("id") Long id, @RequestBody JobPosting jobPosting) throws BusinessServiceException{
        // Fetch the Employee by ID using the service
        Employee employee = empService.getEmployeeeById(id);
            logger.info(employee.getFullName()+" JobPosted Successfully");
        if (employee != null) {
            jobPosting.setEmployee(employee);
            empService.saveJobPostings(jobPosting); // Save the job posting
            return ResponseEntity.ok(jobPosting); // Return 200 OK with job posting
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // Return 404 NOT_FOUND if employee not found
        }
    }

    /**
     * Handles requests for paginated job postings.
     *
     * @param page the current page number to retrieve (zero-based index).
     * @param size the number of job postings per page.
     * @return a ResponseEntity containing a map with pagination details and job postings,
     *         or an HTTP 500 status code in case of an error.
     * @throws BusinessServiceException if there is an issue during service execution.
     */
    @GetMapping(EmployeeURLConstants.EMPLOYEE_LISTOFJOBPOSTINGS)
    public ResponseEntity<Map<String, Object>> getPaginatedJobPostings(
            @PathVariable int page,
            @PathVariable int size) throws BusinessServiceException{
        try {
            Map<String, Object> response = empService.getallJobPosting(page, size);
            logger.info("Pagination is working fine");
            return ResponseEntity.ok(response);
        } catch (BusinessServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    /**
     * Fetches the profile of an employee (job seeker) by their ID.
     *
     * @param id The unique identifier of the employee whose profile is to be fetched.
     * @return A ResponseEntity containing the employee details if found (HTTP 200),
     *         or a 404 Not Found status if the employee does not exist.
     * @throws BusinessServiceException If there is an issue in the service layer
     *         while fetching the employee data.
     */


    @GetMapping(EmployeeURLConstants.EMPLOYEE_VIEWPROFILE)
    public ResponseEntity<Employee> getEmployeeProfile(@PathVariable Long id) throws BusinessServiceException  {
        Employee employee = empService.getEmployeeeById(id);
        logger.info("Fetching the Employee Profile");
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }

    /**
     * Updates the details of an existing employee in the system.
     *
     * This method takes the employee ID and an updated `Employee` object,
     * performs the necessary checks and updates the employee record in the database.
     *
     * If the employee with the given ID exists, their details will be updated. If not,
     * a `BusinessServiceException` will be thrown.
     *
     * @param id The ID of the employee to be updated.
     * @param updatedEmployee The updated `Employee` object containing the new data.
     * @return The updated `Employee` object with the new values.
     * @throws BusinessServiceException If an error occurs during the update operation.
     */
    @PutMapping(EmployeeURLConstants.EMPLOYEE_UPDATEPROFILE)
    public ResponseEntity<Employee> updateEmployeeProfile(
            @PathVariable Long id,
            @RequestBody Employee updatedEmployee) throws BusinessServiceException {
        try {
            Employee employee = empService.updateEmployee(id, updatedEmployee);
            logger.info(employee.getFullName()+"updated Successfully");
            return ResponseEntity.ok(employee); // Return the updated JobSeeker object
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if not found
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Return 500 for other errors
        }
    }


    /**
     * Fetches a list of job postings associated with a specific job seeker based on their ID.
     *
     * This method retrieves job postings from the database where the `jobSeekerId` matches the
     * provided ID. If no job postings are found, it returns an empty list. In case of any error during
     * the data retrieval process, a `BusinessServiceException` is thrown.
     *
     * @param id The ID of the job seeker whose job postings need to be fetched.
     * @return A list of `JobPosting` objects associated with the provided job seeker ID.
     * @throws BusinessServiceException If there is an error during the retrieval of job postings.

     */
    @GetMapping(EmployeeURLConstants.EMPLOYEE_VIEWJOBPOSTINGS)
    public List<JobPosting> getJobPostingsByJobSeekerId(@PathVariable Long id) throws BusinessServiceException {
        return empService.getJobPostingsByJobSeekerId(id);
    }


    /**
     * Retrieves the count of distinct job seekers who have applied for a specific job posting.
     *
     * @param jobPostingId The ID of the job posting for which the applicant count is to be fetched.
     * @return The count of distinct applicants (job seekers) who have applied for the specified job posting.
     * @throws BusinessServiceException If an error occurs while fetching the applicant count, such as a database access error or an unexpected issue.
     */
    @GetMapping(EmployeeURLConstants.EMPLOYEE_COUNTJOBS)
    public Long getApplicantCount(@PathVariable Long jobPostingId) throws BusinessServiceException {
        logger.info("to get the count of the JobPostings");
        return empService.getApplicantCount(jobPostingId);
    }

    /**
     * Fetches the list of resumes associated with a specific job posting.
     *
     * @param jobPostingId The ID of the job posting for which resumes are to be retrieved.
     * @return A list of resumes associated with the given job posting ID. If no resumes are found or an error occurs, an empty list is returned.
     * @throws BusinessServiceException If an error occurs during the operation, such as a database access error or any unexpected failure.
     */

    @GetMapping(EmployeeURLConstants.EMPLOYEE_RESUMEDEATILS)
    public ResponseEntity<List<Resume>> getResumesByJobPostingId(@PathVariable Long jobPostingId) throws BusinessServiceException {
        List<Resume> resumes = empService.findResumesByJobPostingId(jobPostingId);  // Updated service method
        if (resumes != null && !resumes.isEmpty()) {
            return ResponseEntity.ok(resumes);
        } else {
            return ResponseEntity.notFound().build();  // Return 404 if no resumes found
        }
    }


    /**
     * Fetches the list of applications associated with a specific job posting ID.
     *
     * @param jobPostingId The ID of the job posting for which applications are to be retrieved.
     * @return A list of applications associated with the given job posting ID. If no applications are found, an empty list is returned.
     * @throws BusinessServiceException If an error occurs during the operation, such as a database access error or any unexpected failure.
     */

    @GetMapping(EmployeeURLConstants.EMPLOYEE_APPLICATION)
    public ResponseEntity<List<Applications>> getApplicationsByJobPostingId(@PathVariable Long jobPostingId) throws BusinessServiceException {
        List<Applications> applications = empService.getApplicationById(jobPostingId);
        if (!applications.isEmpty()) {
            return ResponseEntity.ok(applications);
        } else {
            return ResponseEntity.noContent().build(); // Returns 204 No Content if no applications are found
        }

    }

    /**
     * Updates the application details based on the provided application ID and updated application data.
     *
     * @param id The ID of the application to be updated.
     * @param applications The updated application data that will replace the existing data.
     * @return The updated application object after the update has been applied.
     * @throws BusinessServiceException If there is an error while updating the application, such as a database access error,
     *                                   or if the application with the given ID does not exist.
     */

    @PutMapping(EmployeeURLConstants.EMPLOYEE_UPDATEAPPLICATION)
    public ResponseEntity<Applications> updateApplication(
            @PathVariable Long id,
            @RequestBody Applications applications) throws BusinessServiceException{
        Applications application = empService.updatApplication(id, applications);
        logger.info("Application is Updated successfully");
        return ResponseEntity.ok(application); // Return the updated application
    }

    @PostMapping(EmployeeURLConstants.SHORTLISTED)
    public ResponseEntity<?> createApplication(
            @PathVariable("jobPostingId") Long jobPostingId,
            @PathVariable("jobSeekerId") Long jobSeekerId,
            @PathVariable(value = "applicationId", required = false) Long applicationId
    ) {
        try {
            JobSeeker jobSeeker = jobService.getJobSeekerById(jobSeekerId);
            JobPosting jobPosting = jobService.getJobPostingById(jobPostingId);
            Applications applications = empService.getStatusByApplicationId(applicationId);
            logger.info("Email Sent Successfully");
            // Optional application ID handling
          /*  Applications application = null;
            if (applicationId != null) {
                application = (Applications) empService.getApplicationById(applicationId);
            }*/

            // Construct the email body based on status
            String emailBody = applications != null
                    ? "Thank you Your application status is: " + applications.getStatus()
                    : "Thank you for registering with RevHire!";

            emailService.sendEmail(
                    jobSeeker.getEmail(),
                    "Welcome to RevHire! " + jobPosting.getCompanyName(),
                    emailBody
            );

            return ResponseEntity.ok("Application created and email sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating application: " + e.getMessage());
        }
    }


    /**
     * Deletes a job posting identified by its ID.
     *
     * <p>This endpoint handles HTTP DELETE requests to remove a job posting from the database
     * based on the provided job posting ID. It invokes the service layer to handle the deletion logic.
     * If the job posting is successfully deleted, it returns a HTTP 204 No Content response.
     *
     * @param jobPostingId the ID of the job posting to be deleted
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content) on successful deletion
     * @throws BusinessServiceException if any business logic validation or errors occur during deletion
     */

    @DeleteMapping(EmployeeURLConstants.DeleteJobposting)
    public ResponseEntity<Void> deleteJobPosting(@PathVariable("id") Long jobPostingId)  throws BusinessServiceException{
        empService.deleteJobPostingById(jobPostingId);
        logger.info("Jobposting deleted successfully ");
        return ResponseEntity.noContent().build(); // HTTP 204
    }


}










