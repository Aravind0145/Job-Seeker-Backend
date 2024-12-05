package com.Aravind.demo.Dao;

import com.Aravind.demo.Exception.DataServiceException;
import com.Aravind.demo.Service.EmployeeService;
import com.Aravind.demo.entity.Applications;
import com.Aravind.demo.entity.Employee;
import com.Aravind.demo.entity.JobPosting;
import com.Aravind.demo.entity.Resume;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

@Service
public interface EmployeeDao {

    /**
     * Saves an employee object to the database.
     *
     * @param employee The employee object to be saved.
     * @throws DataServiceException If there is any error during the save operation.
     */
    void saveEmployees(Employee employee) throws DataServiceException;

    /**
     *
     * Retrieves the role of an employee based on their email and password.
     *
     * @param email The email of the employee.
     * @param password The password of the employee.
     * @return The role of the employee if found.
     * @throws DataServiceException If there is an error during database interact
     */
    public String getRoleByEmailAndPassword(String email,String password) throws DataServiceException;

    /**
     * Retrieves the ID of an employee based on their email and password.
     *
     * @param email The email of the employee.
     * @param password The password of the employee.
     * @return The ID of the employee if found.
     * @throws DataServiceException If there is an error during database interaction or if no result is found.
     */
    Long getIdByEmailAndPassword(String email,String password) throws DataServiceException;

    /**
     * Retrieves the full name of an employee based on their email and password.

     * @param email The email of the employee.
     * @param password The password of the employee.
     * @return The full name of the employee if found.
     * @throws DataServiceException If there is an error during database interaction or if no result is found.

     */
    String getNameByEmailAndPassword(String email,String password) throws DataServiceException;

    /**
     * Updates the password for an employee based on the provided email.
     * @param email The email of the employee whose password needs to be updated.
     * @param password The new password to be set for the employee.
     * @return {@code true} if the password was successfully updated, {@code false} if no employee with the provided email was found.
     * @throws DataServiceException If there is an error updating the password in the database, including Hibernate errors.
     */
    boolean updateEmployeePassword(String email,String password) throws DataServiceException;

    /**
     * Checks if an employee exists with the given email.
     *
     * @param email The email address to check for existence in the database.
     * @return {@code true} if an employee with the specified email exists, {@code false} otherwise.
     * @throws DataServiceException If there is an error while querying the database.
     */
    boolean isEmailExists(String email) throws DataServiceException;

    /**
     * Retrieves an Employee from the database using their ID.
     *
     * <p>This method fetches the Employee object by ID from the database. If the employee is not found,
     * a {@link DataServiceException} is thrown.</p>
     *
     * @param id The ID of the employee to retrieve.
     * @return The Employee object associated with the specified ID.
     * @throws DataServiceException If there is an error during the Hibernate operation, such as a database connection issue.
     */
    Employee getEmployeeeById(Long id) throws DataServiceException;

    /**
     *
     * Saves the provided job posting to the database.
     * If there is an error during the saving process, a custom exception is thrown.
     *
     * @param jobposting The job posting to be saved.
     * @throws DataServiceException if there is an error during the save operation
     */
    void saveJobPostings(JobPosting jobposting) throws DataServiceException;

    /**
     * Retrieves all job postings from the database.
     * This method executes a HQL query to fetch all the job postings and handles various exceptions that may occur.
     * If an error occurs during the process, an appropriate exception will be thrown, and an empty list is returned.
     *
     * @return A list of all job postings in the database, or an empty list if an error occurs.
     * @throws DataServiceException if there is an error during the retrieval process, such as a Hibernate or data access issue.

     */
    Map<String, Object> getallJobPosting(int page, int size) throws DataServiceException;

    /**
     * Updates an existing Employee record in the database.
     *
     * @param id The ID of the Employee to update.
     * @param updatedEmployee The updated Employee object containing new data.
     * @return The updated Employee object.
     * @throws DataServiceException If there is an error in the database or the employee is not found.
     */
    Employee  updateEmployee(Long id, Employee updatedEmployee) throws DataServiceException;


    /**
     * Fetches all job postings associated with a specific job seeker based on their ID.
     *
     * This method retrieves a list of `JobPosting` entities from the database where
     * the `jobSeekerId` matches the provided ID. If no job postings are found, it returns
     * an empty list. If an error occurs during the query, a `DataServiceException` is thrown.
     *
     * @param id The ID of the job seeker whose job postings need to be fetched.
     * @return A list of `JobPosting` objects associated with the provided job seeker ID.
     * @throws DataServiceException If there is an error during the database query or transaction.
     */

    List<JobPosting> getJobPostingsByJobSeekerId(Long id) throws DataServiceException;


    /**
     * Retrieves the count of distinct job seekers who have applied for a specific job posting.
     *
     * @param jobPostingId The ID of the job posting for which the applicant count is to be fetched.
     * @return The count of distinct applicants (job seekers) who have applied for the specified job posting.
     * @throws DataServiceException If an error occurs while querying the database, including any Hibernate exceptions or other unexpected issues.

     */
    Long getApplicantCount(Long jobPostingId) throws DataServiceException;

    /**
     * Retrieves the list of resumes associated with a specific job posting.
     *
     * @param jobPostingId The ID of the job posting for which resumes are to be fetched.
     * @return A list of resumes associated with the given job posting ID.
     * @throws DataServiceException If an error occurs while fetching the resumes, such as a database access error or a transaction issue.
     */
    List<Resume> findResumesByJobPostingId(Long jobPostingId) throws DataServiceException;

    /**
     *
     * Fetches the list of applications associated with a specific job posting ID.
     *
     * @param id The ID of the job posting for which applications are to be retrieved.
     * @return A list of applications associated with the given job posting ID. If no applications are found, an empty list is returned.
     * @throws DataServiceException If an error occurs during the operation, such as a database access error or any unexpected failure.
     */
    List<Applications> getApplicationById(Long id) throws DataServiceException;

    /**
     * Updates the application details based on the provided application ID and updated application data.
     *
     * @param id The ID of the application to be updated.
     * @param applications The updated application data that will replace the existing data.
     * @return The updated application object after the update has been applied.
     * @throws DataServiceException If there is an error while updating the application, such as a database access error,
     *                               or if the application with the given ID does not exist.
     */

    Applications updatApplication(Long id, Applications applications) throws DataServiceException;



    Applications getStatusByApplicationId(Long applicationId) throws DataServiceException;

    void deleteJobPostingById(Long id);

}








