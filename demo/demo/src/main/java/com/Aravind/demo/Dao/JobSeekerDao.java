package com.Aravind.demo.Dao;

import com.Aravind.demo.Exception.DataServiceException;
import com.Aravind.demo.entity.*;

import java.util.List;

public interface JobSeekerDao {

    /**
     * @param jobSeeker The JobSeeker object to be saved.
     *                  This should not be null and must contain valid information
     *                  for the fields that are required by the JobSeeker entity.
     *
     * @throws DataServiceException If there is an error during the saving process, such as a database constraint violation.
     *
     */
    void saveJobseeker(JobSeeker jobSeeker) throws DataServiceException;

    /**
     * Retrieves the role of the JobSeeker based on the provided email and password.
     * This method queries the database to find the role associated with the given email
     * and password. If no JobSeeker is found or if multiple entries are found, a
     * {@link DataServiceException} will be thrown.
     *
     * @param email The email of the JobSeeker whose role is to be retrieved.
     *              This should be a valid, non-null email address.
     * @param password The password of the JobSeeker. This should be a valid, non-null password.
     *
     * @return The role of the JobSeeker as a {@link String}.
     *         This represents the JobSeeker's role (e.g., "admin", "user").
     *
     * @throws DataServiceException If an error occurs while retrieving the role or if no
     *                              matching JobSeeker is found for the provided email
     *                              and password, or if multiple results are found.
     *                              This exception is thrown in case of database issues or
     *                              unexpected results (e.g., multiple matches).
     */
    String getRoleByEmailAndPassword(String email, String password) throws DataServiceException;

    /**
     * Retrieves the ID of the JobSeeker based on the provided email and password.
     * This method queries the database to find the JobSeeker's ID associated with the
     * provided email and password. If no JobSeeker is found or if multiple entries
     * are found, a {@link DataServiceException} will be thrown.

     * @param email The email of the JobSeeker whose ID is to be retrieved.
     *              This should be a valid, non-null email address.
     * @param password The password of the JobSeeker. This should be a valid, non-null password.
     *
     * @return The ID of the JobSeeker as a {@link Long}.
     *         This represents the unique identifier of the JobSeeker.
     *
     * @throws DataServiceException If an error occurs while retrieving the ID or if no
     *                              matching JobSeeker is found for the provided email
     *                              and password, or if multiple results are found.
     *                              This exception is thrown in case of database issues or
     *                              unexpected results (e.g., multiple matches).
     */
    Long getIdByEmailAndPassword(String email,String password) throws DataServiceException;

    /**
     * Retrieves the full name of the JobSeeker based on the provided email and password.
     * This method queries the database to find the JobSeeker's full name associated with the
     * provided email and password. If no JobSeeker is found or if multiple entries
     * are found, a {@link DataServiceException} will be thrown.
     *
     * @param email The email of the JobSeeker whose full name is to be retrieved.
     *              This should be a valid, non-null email address.
     * @param password The password of the JobSeeker. This should be a valid, non-null password.
     *
     * @return The full name of the JobSeeker as a {@link String}.
     *         This represents the JobSeeker's name, if found.
     *
     * @throws DataServiceException If an error occurs while retrieving the name or if no
     *                              matching JobSeeker is found for the provided email
     *                              and password, or if multiple results are found.
     *                              This exception is thrown in case of database issues or
     *                              unexpected results (e.g., multiple matches).

     */
    String getNameByEmailAndPassword(String email, String password) throws DataServiceException;

    /**
     * Saves a JobSeeker's resume to the database.
     * This method is responsible for persisting the provided {@link Resume} entity. If any issues arise during
     * the save operation (such as database or transaction problems), a custom {@link DataServiceException} is thrown.
     *
     * @param resume The {@link Resume} object to be saved. This object should be properly populated with valid
     *               data before calling the method.
     *
     * @throws DataServiceException If an error occurs while saving the resume to the database. This could occur
     *                               due to issues such as:
     *                               <ul>
     *                                   <li>Database errors during the save operation (e.g., constraint violations, connection issues).</li>
     *                                   <li>Problems with the Hibernate session or transaction (e.g., unable to commit the transaction).</li>
     *                               </ul>
     */
    void saveJobResume(Resume resume) throws DataServiceException;

    boolean checkResumeExistence(Long jobseekerid) throws DataServiceException;


    /**
     * Retrieves a JobSeeker entity by its unique identifier.
     * This method queries the database to fetch a JobSeeker using the provided ID.
     * If no JobSeeker is found with the given ID, it will return {@code null}.
     *
     * @param id The unique identifier of the JobSeeker to retrieve.
     *           This must be a valid, non-null ID that corresponds to an existing JobSeeker in the database.
     *
     * @return The JobSeeker object associated with the provided ID, or {@code null} if no such JobSeeker exists.
     *
     * @throws DataServiceException If an error occurs while retrieving the JobSeeker, such as:
     *                               <ul>
     *                                   <li>Database connectivity issues</li>
     *                                   <li>Hibernate session or transaction failures</li>
     *                                   <li>Other database or data access issues</li>
     *                               </ul>
     */
    JobSeeker getJobSeekerId(Long id) throws DataServiceException;


    Resume getResumeByJobseekerId(Long jobseekerId);

    /**
     * Updates the password for a JobSeeker based on the provided email.
     *
     * @param email The email of the JobSeeker whose password is to be updated.
     * @param password The new password to be set for the JobSeeker. This password should be securely hashed before being passed to this method.
     * @return A boolean indicating whether the password was successfully updated. Returns true if the update was successful, false otherwise.
     * @throws DataServiceException If an error occurs
     *                  while updating the password, such as a problem with
     *                  the database connection or if no JobSeeker is found with the
     *                  provided email.
     */
    boolean updateJobSeekerPassword(String email,String password) throws DataServiceException;

    /**
     * Checks if a JobSeeker exists with the given email.
     *
     * @param email The email of the JobSeeker to check for existence.
     * @return {@code true} if a JobSeeker with the given email exists, {@code false} otherwise.
     * @throws DataServiceException If an error occurs while querying the database.
     */
    boolean isEmailExists(String email) throws DataServiceException;


    /**
     * Submits an application by saving it to the database.
     *
     * This method is responsible for accepting an application object and saving it
     * to the database. It will throw a `DataServiceException` if an error occurs
     * during the process.
     *
     * @param applications The application object to be submitted.
     * @throws DataServiceException If an error occurs while saving the application
     *         or during transaction management.
     */
    void submitApplication(Applications applications) throws DataServiceException;

    /**
     * Retrieves a JobPosting by its ID from the database.
     *
     * This method fetches a JobPosting object based on the provided job posting ID. If the job posting
     * with the given ID is not found, the method returns null or throws a custom exception depending on
     * the implementation details.
     *
     * @param JobpostingId The ID of the job posting to retrieve.
     * @return The JobPosting object corresponding to the provided ID.
     * @throws DataServiceException If an error occurs while fetching the JobPosting from the database.
     */
    JobPosting getJobPostingById(long JobpostingId) throws DataServiceException;

    /**

     * Retrieves a Resume by its ID from the database.

     * This method fetches a Resume object based on the provided resume ID. If the resume
     * with the given ID is not found, the method returns null or throws a custom exception depending on
     * the implementation details.
     *
     * @param resumeid The ID of the resume to retrieve.
     * @return The Resume object corresponding to the provided ID.
     * @throws DataServiceException If an error occurs while fetching the Resume from the database.
     */
    Resume getResumeById(Long resumeid) throws DataServiceException;

    /**
     * Updates the details of an existing JobSeeker in the database.
     *
     * @param id the ID of the JobSeeker to be updated.
     * @param updatedJobSeeker the JobSeeker object containing the updated information.
     * @return the updated {@link JobSeeker} object.
     * @throws DataServiceException if there is an error during the update process,
     *         such as when the JobSeeker is not found or if a database error occurs.
     */
    JobSeeker updateJobSeeker(Long id,JobSeeker updatedJobSeeker) throws DataServiceException;

    /**
     * Retrieves a list of applications submitted by a specific JobSeeker.
     *
     * @param jobseekerid the ID of the JobSeeker whose applications are to be retrieved.
     * @return a list of {@link Applications} objects associated with the given JobSeeker.
     * @throws DataServiceException if there is an error while fetching the applications,
     *         such as a database error or if the JobSeeker's applications cannot be retrieved.

     */
    List<Applications> getApplicationsByJobSeeker(Long jobseekerid) throws  DataServiceException;

    /**
     * Withdraws an application submitted by a job seeker.
     *
     * @param jobSeekerId The ID of the job seeker who submitted the application.
     * @param applicationId The ID of the application to withdraw.
     * @return true if the application was successfully withdrawn, false if the application was not found.
     * @throws DataServiceException if there is an error during the withdrawal process.
     */
    boolean withdrawApplication(long jobSeekerId,Long applicationId) throws DataServiceException;

    /**
     *
     * Searches for job postings based on the given parameters (job title, location, and experience).
     * If any parameter is null or empty, it is ignored in the search.
     *
     * @param jobTitle The title of the job to search for.
     * @param location The location where the job is located.
     * @param experience The required experience for the job.
     * @return A list of JobPosting objects matching the search criteria.
     * @throws DataServiceException If an error occurs while searching for job postings.
     */
    List<JobPosting> searchJobs(String jobTitle,String location,String experience) throws DataServiceException;

    /**
     /**
     * Updates the Resume entity with the provided details and the given ID.
     *
     * @param resume The updated Resume object.
     * @param id The ID of the Resume to be updated.
     * @return The updated Resume object.
     * @throws DataServiceException If an error occurs while updating the Resume.

     */
    Resume updatedResume(Resume resume,Long id) throws DataServiceException;

}
