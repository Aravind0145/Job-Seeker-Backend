package com.Aravind.demo.Service;


import com.Aravind.demo.Exception.BusinessServiceException;
import com.Aravind.demo.entity.Applications;
import com.Aravind.demo.entity.JobPosting;
import com.Aravind.demo.entity.JobSeeker;
import com.Aravind.demo.entity.Resume;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobService {

    /**
     * Saves the provided JobSeeker entity to the database.
     *
     * This method performs the necessary steps to persist the JobSeeker object into the database.
     * If an error occurs during the save process (such as a database connectivity issue),
     * a {@link BusinessServiceException} will be thrown.
     *
     *
     * @param jobSeeker The {@link JobSeeker} object to be saved.
     *                  This object must contain valid data for the fields that are required
     *                  by the JobSeeker entity (e.g., name, email, etc.).
     *                  It should not be {@code null}.
     *
     * @throws BusinessServiceException If an error occurs while saving the JobSeeker
     *                                  to the database. This exception is thrown to signal
     *                                  issues related to business logic or database interactions.
     */
    void saveJobSeeker(JobSeeker jobSeeker) throws BusinessServiceException;

    /**
     * Retrieves the role of the JobSeeker based on the provided email and password.
     * This method queries the database to find the role associated with the JobSeeker who
     * matches the given email and password. If no such JobSeeker exists or multiple
     * entries are found, a {@link BusinessServiceException} will be thrown.
     *
     * @param email The email of the JobSeeker whose role is to be retrieved.
     *              This should be a valid, non-null email address.
     * @param password The password of the JobSeeker. This should be a valid, non-null password.
     *
     * @return The role of the JobSeeker if found. This will return a string representing the
     *         JobSeeker's role (e.g., "admin", "user", etc.).
     *
     * @throws BusinessServiceException If an error occurs while fetching the role or if no
     *                                  matching JobSeeker is found for the provided email
     *                                  and password. This exception is thrown to signal
     *                                  issues such as no result or multiple results found.
     */
    public String getRoleByEmailAndPassword(String email,String password) throws BusinessServiceException;

    /**
     * Retrieves the unique ID of the JobSeeker based on the provided email and password.
     * <p>
     * This method queries the database to find the ID of the JobSeeker associated with the
     * given email and password. If no such JobSeeker exists or if multiple entries are found,
     * a {@link BusinessServiceException} will be thrown.
     * </p>
     *
     * @param email The email of the JobSeeker whose ID is to be retrieved.
     *              This should be a valid, non-null email address.
     * @param password The password of the JobSeeker. This should be a valid, non-null password.
     *
     * @return The ID of the JobSeeker if found. This returns a {@link Long} representing
     *         the JobSeeker's ID.
     *
     * @throws BusinessServiceException If an error occurs while fetching the ID or if no
     *                                  matching JobSeeker is found for the provided email
     *                                  and password, or if multiple results are found.
     *                                  This exception signals issues like no result or
     *                                  multiple results found.
     */
    Long getIdByEmailAndPassword(String email,String password) throws BusinessServiceException;

    /**
     * Retrieves the full name of the JobSeeker based on the provided email and password.
     * This method queries the database to find the full name of the JobSeeker associated
     * with the given email and password. If no such JobSeeker exists or if multiple entries
     * are found, a {@link BusinessServiceException} will be thrown.
     *
     * @param email The email of the JobSeeker whose full name is to be retrieved.
     *              This should be a valid, non-null email address.
     * @param password The password of the JobSeeker. This should be a valid, non-null password.
     *
     * @return The full name of the JobSeeker if found. This returns a {@link String} representing
     *         the JobSeeker's full name.
     *
     * @throws BusinessServiceException If an error occurs while fetching the name or if no
     *                                  matching JobSeeker is found for the provided email
     *                                  and password, or if multiple results are found.
     *                                  This exception signals issues like no result or
     *                                  multiple results found.
     */
    String getNameByEmailAndPassword(String email,String password) throws BusinessServiceException;

    /**
     * Saves a JobSeeker's resume as part of the business service logic.
     * This method persists the provided {@link Resume} entity to the database.
     * If an error occurs during the save process, a {@link BusinessServiceException} is thrown.
     *
     * @param resume The {@link Resume} object to be saved. The resume should contain all necessary details,
     *               such as the JobSeeker's resume information, before calling the method.
     *
     * @throws BusinessServiceException If an error occurs while saving the resume. This exception is thrown for
     *                                  issues related to the business logic, such as:
     *                                  <ul>
     *                                      <li>Database errors (e.g., constraint violations, connection issues).</li>
     *                                      <li>Unexpected issues during the transaction or session management.</li>
     *                                  </ul>
     */
    void saveJobResume(Resume resume) throws BusinessServiceException;

    /**
     * Checks whether a resume exists for a given jobseeker.
     *
     * This method queries the database to check if a resume is associated with the
     * specified jobseeker ID. It returns true if a resume exists, otherwise false.
     *
     * @param jobseekerid The ID of the jobseeker whose resume existence is being checked.
     * @return {@code true} if the jobseeker has a resume, {@code false} otherwise.
     * @throws BusinessServiceException if an error occurs while checking the resume existence, such as database connectivity issues.
     */

    boolean checkResumeExistence(Long jobseekerid) throws BusinessServiceException;

    /**
     * Retrieves a JobSeeker entity by its unique identifier.
     * This method queries the database to fetch a JobSeeker using the provided ID.
     * If no JobSeeker is found with the given ID, it will return {@code null}.
     *
     * @param id The unique identifier of the JobSeeker to retrieve.
     *           This should be a valid, non-null ID corresponding to an existing JobSeeker in the database.
     *
     * @return The JobSeeker object associated with the provided ID, or {@code null} if no such JobSeeker exists.
     *
     * @throws BusinessServiceException If an error occurs during the retrieval process, such as:
     *                                   <ul>
     *                                       <li>Database connectivity issues</li>
     *                                       <li>Service layer exceptions</li>
     *                                       <li>Other business logic or data access issues</li>
     *                                   </ul>
     */
    JobSeeker getJobSeekerById(Long id) throws BusinessServiceException;

    Resume getResumeByJobseekerId(Long id);

    /**
     * Updates the password for a JobSeeker based on the provided email.
     *
     * @param email The email of the JobSeeker whose password is to be updated.
     * @param password The new password to be set for the JobSeeker.
     *                This password should be securely hashed before being passed to this method.
     * @return A boolean indicating whether the password was successfully updated.
     *                  Returns true if the update was successful, false otherwise.
     * @throws BusinessServiceException If an error occurs
     *                      while updating the password, such as
     *                      if no JobSeeker is found with the provided
     *                      email or if there is a business logic failure.
     */
    boolean updateJobSeekerPassword(String email,String password) throws BusinessServiceException;

    /**
     * Checks whether a JobSeeker email exists in the database.
     *
     * This method verifies if an email is already registered for a JobSeeker
     * in the database. It can be used to avoid duplicate email registrations or
     * to confirm the presence of a JobSeeker account associated with the given email.
     *
     * @param email The email address to check for existence.
     * @return {@code true} if the email exists, {@code false} otherwise.
     * @throws BusinessServiceException If an error occurs during the email existence check, such as
     *         database access issues or invalid input.
     */
    boolean isEmailExists(String email) throws BusinessServiceException;

    /**
     * Submits a job application for a JobSeeker.
     *
     * This method handles the process of saving a job application to the database.
     * It associates the application with the relevant JobSeeker, JobPosting, and Resume
     * details before persisting the application.
     *
     * @param applications The {@link Applications} object containing details of the job application
     *                     such as the JobSeeker, JobPosting, and Resume information.
     * @throws BusinessServiceException If an error occurs during the submission process,
     *         such as database access issues or invalid application details.
     */
    void submitApplication(Applications applications) throws BusinessServiceException;

    /**
     * Retrieves a JobPosting by its unique identifier.
     *
     * This method fetches a {@link JobPosting} object from the database based on the provided ID.
     * It ensures that the JobPosting is correctly retrieved or an appropriate exception is thrown
     * if it does not exist.
     *
     * @param jobpostingId The unique identifier of the JobPosting to retrieve.
     * @return The {@link JobPosting} object corresponding to the provided ID.
     * @throws BusinessServiceException If an error occurs during the retrieval process or if the
     *         JobPosting is not found.
     */
    JobPosting getJobPostingById(Long jobpostingId) throws BusinessServiceException;

    /**
     * Retrieves a Resume by its unique identifier.
     *
     * This method fetches a {@link Resume} object from the database based on the provided ID.
     * It ensures that the Resume is correctly retrieved or an appropriate exception is thrown
     * if it does not exist.
     *
     * @param resumeid The unique identifier of the Resume to retrieve.
     * @return The {@link Resume} object corresponding to the provided ID.
     * @throws BusinessServiceException If an error occurs during the retrieval process or if the
     *         Resume is not found.
     */
    Resume  getResumeById(Long resumeid) throws BusinessServiceException;

    /**
     * Updates the profile details of an existing JobSeeker.
     *
     * @param id the ID of the JobSeeker to be updated.
     * @param updatedJobSeeker the updated {@link JobSeeker} object containing the new information.
     * @return the updated {@link JobSeeker} object after applying the changes.
     * @throws BusinessServiceException if there is an error while updating the JobSeeker's profile,
     *         such as when the JobSeeker is not found or any other business-related error occurs.
     */
    JobSeeker updateJobSeeker(Long id,JobSeeker updatedJobSeeker) throws BusinessServiceException;

    /**
     * Retrieves a list of job applications submitted by a specific JobSeeker.
     *
     * @param jobseekerid the ID of the JobSeeker whose applications are to be fetched.
     * @return a list of {@link Applications} objects submitted by the JobSeeker.
     * @throws BusinessServiceException if there is an error while retrieving the applications,
     *         such as when the JobSeeker is not found or any other business-related error occurs.

     */
    List<Applications> getApplicationsByJobSeeker(Long jobseekerid) throws BusinessServiceException;

    /**
     * Withdraws an application submitted by a job seeker.
     *
     * @param jobSeekerId The ID of the job seeker who submitted the application.
     * @param applicationId The ID of the application to withdraw.
     * @return true if the application was successfully withdrawn, false if the application was not found.
     * @throws BusinessServiceException if there is an error during the withdrawal process.
     */
    boolean withdrawApplication(Long jobSeekerId,Long applicationId) throws BusinessServiceException;

    /**
     *
     * Searches for job postings based on the provided job title, location, and experience.
     * If any of the parameters is null or empty, it is ignored in the search.
     *
     * @param jobtitle The title of the job to search for. Can be a partial string.
     * @param location The location where the job is located. Can be a partial string.
     * @param experience The required experience for the job. Can be a partial string.
     * @return A list of JobPosting objects that match the search criteria.
     * @throws BusinessServiceException If an error occurs while searching for job postings.
     */
    List<JobPosting> searchJobs(String jobtitle,String location,String experience) throws BusinessServiceException;

    /**
     * Updates the Resume entity with the provided details and the given ID.
     *
     * @param resume The updated Resume object containing the new data.
     * @param id The ID of the Resume to be updated.
     * @return The updated Resume object after the update operation is successful.
     * @throws BusinessServiceException If an error occurs while updating the Resume, such as
     *                                  a HibernateException or other database-related errors.
     */
    Resume updatedResume(Resume resume,Long id) throws BusinessServiceException;

}
