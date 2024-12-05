package com.Aravind.demo.Controller;


import com.Aravind.demo.Constants.JobSeekerURLConstant;
import com.Aravind.demo.DemoApplication;
import com.Aravind.demo.Exception.BusinessServiceException;
import com.Aravind.demo.Service.EmailService;
import com.Aravind.demo.Service.JobService;
import com.Aravind.demo.entity.Applications;
import com.Aravind.demo.entity.JobPosting;
import com.Aravind.demo.entity.JobSeeker;
import com.Aravind.demo.entity.Resume;
import io.micrometer.observation.GlobalObservationConvention;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    @Autowired
    private EmailService emailService;


    /**
     * Handles the registration of a new JobSeeker and sends a welcome email.
     * This method accepts a JobSeeker object, saves it to the database,
     * and sends a confirmation email to the provided email address.
     * @param jobSeeker The JobSeeker object containing the details to be saved.
     *                  This object should not be null, and it must have the necessary fields like
     *                  name, email, and other required attributes for registration.
     *
     * @return The saved JobSeeker object, which is returned after successfully saving the data to the database.

     * @throws BusinessServiceException  If there is an error during the JobSeeker registration process,
     *                                    such as a failure in saving the JobSeeker to the database or sending the email.
     *                                    or else any connection error occurs it is going to handel the Hibernate Exception.
     *                                    This could occur due to invalid data, database errors, or issues
     *                                    with the email service.
     */


    @PostMapping(JobSeekerURLConstant.JOBSEEKER_REGISTER)
    public JobSeeker AddJobsSeekers(@RequestBody JobSeeker jobSeeker) throws BusinessServiceException {
        jobService.saveJobSeeker(jobSeeker);
        logger.info("Job seeker is Registered Successfully");
        emailService.sendEmail(jobSeeker.getEmail(),
                "Welcome to RevHire!"+jobSeeker.getFullName(),
                "Thank you for registering with RevHire!");// Save the JobSeeker entity using saveEmployees
        return jobSeeker;  // Return the saved JobSeeker object
    }

    /**
     * Handles the login process for a JobSeeker by validating their email and password.
     * <p>
     * This method takes the email and password from the request, validates the credentials by
     * checking the role, ID, and full name of the JobSeeker. If valid, it returns a success
     * response with the JobSeeker's details; otherwise, it returns an unauthorized response.
     * </p>
     *
     * @param loginRequest A {@link Map} containing the email and password of the JobSeeker.
     *                     The map must contain the following keys:
     *                     <ul>
     *                     <li>email - The JobSeeker's email address (non-null).</li>
     *                     <li>password - The JobSeeker's password (non-null).</li>
     *                     </ul>
     *
     * @return A {@link ResponseEntity} containing a map with the JobSeeker's role, ID, and full name
     *         if the login is successful. If the credentials are invalid, an {@link HttpStatus#UNAUTHORIZED}
     *         response will be returned with no body.
     *
     * @throws BusinessServiceException If an error occurs while retrieving the JobSeeker's details
     *                                   (role, ID, or full name) from the service layer.
     *                                   This may occur if there are issues with the database or logic
     *                                   that handles login.
     */


    @PostMapping(JobSeekerURLConstant.JOBSSEEKER_LOGIN)
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) throws BusinessServiceException{
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        // Assuming you have a method to retrieve the user's role and ID
        String role = jobService.getRoleByEmailAndPassword(email, password);
        Long id = jobService.getIdByEmailAndPassword(email, password);
        String fullName = jobService.getNameByEmailAndPassword(email,password);
        logger.info("job seeker is logged in");
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
     * Retrieves the resume details for a job seeker by their ID.
     * If the resume is found, a 200 OK response is returned with the resume data.
     * If no resume is found for the given ID, a 404 Not Found response is returned.
     *
     * @param id The ID of the job seeker whose resume is to be fetched.
     * @return ResponseEntity containing the resume details and an appropriate HTTP status:
     *         - 200 OK with resume data if found,
     *         - 404 Not Found if no resume is found for the provided ID.
     * @throws BusinessServiceException If there is any service-related issue while fetching the resume.
     */

    @GetMapping(JobSeekerURLConstant.JOBSEEKER_RESUMEDETAILS)
    public ResponseEntity<Resume> getResume(@PathVariable Long id) throws BusinessServiceException {
        Resume resume = jobService.getResumeByJobseekerId(id);
        return resume != null ? ResponseEntity.ok(resume) : ResponseEntity.notFound().build();
    }

    /**
     * Updates the password for a JobSeeker based on the provided email.
     *
     * @param passwordUpdateRequest A map containing the email and new password for the JobSeeker.
     *                              The map should have the keys "email" and "password".
     * @return A {@link ResponseEntity} containing a response map with a message indicating the result of the update operation.
     *         The response will either contain a success message or an error message if the email is invalid or the JobSeeker is not found.
     * @throws BusinessServiceException If an error occurs
     *          during the password update process.
     */


    @PutMapping(JobSeekerURLConstant.JOBSEEKER_UPDATEPASSWORD)
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody Map<String, String> passwordUpdateRequest) throws BusinessServiceException {
        String email = passwordUpdateRequest.get("email");
        String newPassword = passwordUpdateRequest.get("password");

        // Call service to update the password
        boolean isUpdated = jobService.updateJobSeekerPassword(email, newPassword);
        logger.info("password updated successfully");
        Map<String, Object> response = new HashMap<>();
        if (isUpdated) {
            response.put("message", "Password updated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Jobseeker not found or invalid email");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Checks if a JobSeeker exists with the given email.
     *
     * @param email The email address of the JobSeeker to check.
     * @return A {@link ResponseEntity} containing a map with the key "exists"
     *              and a boolean value indicating whether the email exists in the database.
     * @throws BusinessServiceException If an error occurs while
     *                  checking the email existence.
     */

    @GetMapping(JobSeekerURLConstant.JOBSEEKER_CEHCKEMAIL)
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) throws BusinessServiceException {
        boolean exists = jobService.isEmailExists(email);
        logger.info("Checking the email if already it is their");
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }


    /**
     * Adds a resume to a JobSeeker by associating the given resume with the JobSeeker.
     * The method fetches the JobSeeker entity by the provided ID and saves the resume
     * for that JobSeeker. If the JobSeeker is not found, an exception will be thrown and handled in the service layer.
     *
     * @param id The unique identifier of the JobSeeker for whom the resume is being added.
     *           This should be a valid, non-null ID corresponding to an existing JobSeeker.
     * @param resume The {@link Resume} object containing the JobSeeker's resume details.
     *               The object should not be null and must contain valid resume information.
     *
     * @return The saved {@link Resume} object associated with the JobSeeker.
     *
     * @throws BusinessServiceException If an error occurs during the process of saving the resume.
     *                                  This could include issues related to fetching the JobSeeker,
     *                                  or any other business logic failures.
     *                                  <ul>
     *                                      <li>JobSeeker not found</li>
     *                                      <li>Database issues during resume save</li>
     *                                  </ul>
     */

    @PostMapping(JobSeekerURLConstant.JOBSEEKER_RESUME)
    public Resume addResume(@PathVariable("id") Long id, @RequestBody Resume resume) throws BusinessServiceException {
        // Fetch the JobSeeker by ID using Hibernate
        JobSeeker jobSeeker = jobService.getJobSeekerById(id); // Get JobSeeker from the service
        logger.info("fetching the job seeker id and send it to the Resume  ");
        if (jobSeeker != null) {
            resume.setJobSeeker(jobSeeker);
            jobService.saveJobResume(resume); // Save the resume
            return resume;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Seeker not found");
        }
    }


    /**
     * Checks whether a resume exists for a given jobseeker.
     *
     * This method queries the database to check if a resume is associated with the
     * specified jobseeker ID. It returns true if a resume exists, otherwise false.
     *
     * @param jobseekerId The ID of the jobseeker whose resume existence is being checked.
     * @return {@code true} if the jobseeker has a resume, {@code false} otherwise.
     * @throws BusinessServiceException if an error occurs while checking the resume existence, such as database connectivity issues.
     */
    
    @GetMapping(JobSeekerURLConstant.JOBSEEKER_CHECK_RESUME)
    public ResponseEntity<Boolean> checkResumeExistence(@PathVariable("jobseekerId") Long jobseekerId)  throws BusinessServiceException {
        try {
            // Check if resume exists for the jobseeker
            boolean exists = jobService.checkResumeExistence(jobseekerId);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }


    /**
     * Applies to a job posting by submitting an application, including job seeker, job posting, and resume details.
     * Sends a confirmation email to the job seeker upon successful application.
     *
     * @param jobPostingId The ID of the job posting to which the job seeker is applying.
     * @param jobSeekerId The ID of the job seeker applying to the job.
     * @param resumeId The ID of the resume being submitted with the application.
     * @param application The application details containing job seeker, job posting, and resume information.
     * @return The submitted application.
     * @throws BusinessServiceException If any of the entities (job seeker, job posting, or resume) cannot be found,
     *         or if any other error occurs during the application submission process.
     */


    // Updated endpoint to handle dynamic path parameters
   /* @PostMapping(JobSeekerURLConstant.JOBSEEKER_APPLICATIONS)
    public ResponseEntity<Applications> applyToJob(
            @PathVariable Long jobPostingId,
            @PathVariable Long jobSeekerId,
            @PathVariable Long resumeId,
            @RequestBody Applications application) throws BusinessServiceException {

        // Retrieve job seeker, job posting, and resume by their IDs
        JobSeeker jobSeeker = jobService.getJobSeekerById(jobSeekerId);


        JobPosting jobPosting = jobService.getJobPostingById(jobPostingId);
        Resume resume = jobService.getResumeById(resumeId);

        emailService.sendEmail(jobSeeker.getEmail(),
                "Welcome to !",
                "Thank you for applying "+ jobPosting.getCompanyName());//
        logger.info("Applied to job successfully"  +jobSeeker.getFullName());

        application.setJobSeeker(jobSeeker);
        application.setJobPosting(jobPosting);
        application.setResume(resume);

        // jobService.saveJobResume(resume);

        // Save the application
        jobService.submitApplication(application);

        // Return the updated application object
        return ResponseEntity.ok(application);
    }

*/

    @PostMapping(JobSeekerURLConstant.JOBSEEKER_APPLICATIONS)
    public ResponseEntity<Applications> applyToJob(
            @PathVariable Long jobPostingId,
            @PathVariable Long jobSeekerId,
            @PathVariable Long resumeId,
            @RequestBody Applications application) throws BusinessServiceException {

        try {
            // Retrieve job seeker, job posting, and resume by their IDs
            JobSeeker jobSeeker = jobService.getJobSeekerById(jobSeekerId);
            JobPosting jobPosting = jobService.getJobPostingById(jobPostingId);
            Resume resume = jobService.getResumeById(resumeId);

            // Set the job seeker, job posting, and resume into the application
            application.setJobSeeker(jobSeeker);
            application.setJobPosting(jobPosting);
            application.setResume(resume);

            // Call the service method to submit the application
            jobService.submitApplication(application);
            emailService.sendEmail(jobSeeker.getEmail(),
                    "Welcome to !",
                    "Thank you for applying "+ jobPosting.getCompanyName());

            logger.info("Applied to job successfully"  +jobSeeker.getFullName());

            return ResponseEntity.ok(application); // Return HTTP 200 with the application details
        } catch (BusinessServiceException e) {
            // Handle the case where the job seeker has already applied for the job
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // HTTP 400 - Bad Request
        }
    }



    @GetMapping(JobSeekerURLConstant.JOBSEEKER_VIEWPROFILE)
    public ResponseEntity<JobSeeker> getJobSeekerProfile(@PathVariable Long id) {
        JobSeeker jobseeker = jobService.getJobSeekerById(id);
        if (jobseeker != null) {
            return ResponseEntity.ok(jobseeker);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }

    /**
     * Retrieves a Resume by its unique ID.
     *
     * This method handles a GET request to fetch a specific Resume entity
     * identified by the provided ID. If the Resume exists, it is returned
     * with an HTTP 200 status. If the Resume is not found, an HTTP 404 status
     * is returned.
     *
     * @param id The unique identifier of the Resume to retrieve.
     * @return A {@link ResponseEntity} containing the {@link Resume} object if found,
     *         or an HTTP 404 status if the Resume is not found.
     * @throws BusinessServiceException If an error occurs during the retrieval process.
     */

    @GetMapping(JobSeekerURLConstant.JOBSEEEKR_VIEWRESUME)
    public ResponseEntity<Resume> getResumeById(@PathVariable Long id) throws BusinessServiceException{
        Resume resume = jobService.getResumeById(id);
        logger.info("based on the id we are fetching the Resume");
        if (resume != null) {
            return ResponseEntity.ok(resume);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }


    /**
     * Updates the profile of a JobSeeker.
     *
     * This method handles a PUT request to update the details of a specific JobSeeker
     * identified by the provided ID. The updated details are passed in the request body.
     * If the update is successful, the updated JobSeeker object is returned with an HTTP 200 status.
     *
     * @param id The unique identifier of the JobSeeker to be updated.
     * @param updatedJobSeeker The {@link JobSeeker} object containing the updated details.
     * @return A {@link ResponseEntity} containing the updated {@link JobSeeker} object.
     * @throws BusinessServiceException If an error occurs during the update process.
     */
    @PutMapping(JobSeekerURLConstant.JOBSEEKR_UPDATEPROFILE)
    public ResponseEntity<JobSeeker> updateJobSeekerProfile(
            @PathVariable Long id,
            @RequestBody JobSeeker updatedJobSeeker) throws BusinessServiceException {
        JobSeeker jobSeeker = jobService.updateJobSeeker(id, updatedJobSeeker);
        logger.info("Job Seeker Updated Successfully");
        return ResponseEntity.ok(jobSeeker);
    }


    /**
     * Updates the Resume entity with the provided details and the given ID.
     *
     * @param updatedresume The updated Resume object containing the new data.
     * @param id The ID of the Resume to be updated.
     * @return The updated Resume object after the update operation is successful.
     * @throws BusinessServiceException If an error occurs while updating the Resume, such as
     *                                  a HibernateException or other database-related errors.
     */

    @PutMapping(JobSeekerURLConstant.JOBSEEKER_UPDATERESUME)
    public ResponseEntity<Resume> updateResume(
            @RequestBody Resume updatedresume,
            @PathVariable Long id
    ) throws BusinessServiceException { // Declare the exception thrown by the service method

        Resume resume = jobService.updatedResume(updatedresume, id); // Let the exception propagate if there's an issue
            logger.info("Resume Updated Successfully");
        return ResponseEntity.ok(resume); // Return the updated Resume object
    }



    /**
     * Retrieves a list of job applications submitted by a specific JobSeeker.
     *
     * @param jobSeekerId the ID of the JobSeeker whose applications are to be fetched.
     * @return a list of {@link Applications} objects submitted by the JobSeeker.
     * @throws BusinessServiceException if there is an error while retrieving the applications,
     *         such as when the JobSeeker is not found or any other business-related error occurs.
     */
    @GetMapping(JobSeekerURLConstant.JOBSEEKER_APPLIEDJOBS)
    public List<Applications> getApplicationsByJobSeeker(@PathVariable Long jobSeekerId) throws BusinessServiceException {
        logger.info("Get Application based on the JobSeeker");
        return jobService.getApplicationsByJobSeeker(jobSeekerId);
    }

    /**
     * Handles the withdrawal of a job application by a job seeker.
     *
     * @param jobSeekerId The ID of the job seeker withdrawing the application.
     * @param applicationId The ID of the application being withdrawn.
     * @return A ResponseEntity indicating the status of the withdrawal operation:
     *         - 204 No Content if the application was successfully withdrawn,
     *         - 404 Not Found if the application could not be found.
     */

    @DeleteMapping(JobSeekerURLConstant.JOBSEEKER_WITHDRAW)
    public ResponseEntity<Void> withdrawApplication(@PathVariable Long jobSeekerId, @PathVariable Long applicationId) throws
            BusinessServiceException{
        boolean isDeleted = jobService.withdrawApplication(jobSeekerId, applicationId);
            logger.info("With Draw Application Successfully");
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content, successful deletion
        } else {
            return ResponseEntity.status(404).build(); // 404 Not Found, application not found
        }
    }

    /**
     *
     * Searches for job postings based on the provided job title, location, and experience.
     * If any of the parameters is null or empty, it is ignored in the search.
     *
     * @param jobTitle The title of the job to search for. Can be a partial string.
     * @param location The location where the job is located. Can be a partial string.
     * @param experience The required experience for the job. Can be a partial string.
     * @return A list of JobPosting objects that match the search criteria.
     * @throws BusinessServiceException If an error occurs while searching for job postings.
     */

    @GetMapping(JobSeekerURLConstant.JOBSEEKER_SEARCHJOB)
    public ResponseEntity<List<JobPosting>> searchJobs(
            @PathVariable String jobTitle,
            @PathVariable String location,
            @PathVariable String experience) throws BusinessServiceException{

        System.out.println("Job Title: " + jobTitle);
        System.out.println("Location: " + location);
        System.out.println("Experience: " + experience);

        // Call the service to search for jobs
        List<JobPosting> jobPostings = jobService.searchJobs(jobTitle, location, experience);

        // Check if there are job postings and return the appropriate response
        if (jobPostings != null && !jobPostings.isEmpty()) {
            return ResponseEntity.ok(jobPostings); // Return 200 OK with job postings
        } else {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no jobs are found
        }
    }



}









