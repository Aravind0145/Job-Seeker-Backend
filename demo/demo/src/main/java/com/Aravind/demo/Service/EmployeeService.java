package com.Aravind.demo.Service;

import com.Aravind.demo.Exception.BusinessServiceException;
import com.Aravind.demo.entity.Applications;
import com.Aravind.demo.entity.Employee;

import com.Aravind.demo.entity.JobPosting;
import com.Aravind.demo.entity.Resume;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EmployeeService {

    /**
     * Registers a new employee by saving the employee details and sending a welcome email.
     *
     * @param employee The employee object containing the details to be saved.
     * @return The saved Employee object.
     * @throws BusinessServiceException If there is any error during the employee registration process.
     */
    void saveEmployees(Employee employee) throws BusinessServiceException;

    /**
     * Retrieves the employee's ID based on their email and password.
     *
     * @param email The email of the employee.
     * @param password The password of the employee.
     * @return The Role of the employee if found.
     * @throws BusinessServiceException If there is an error while fetching the ID from the data layer.
     */
    public String getRoleByEmailAndPassword(String email,String password) throws BusinessServiceException;

    /**
     * Retrieves the employee's ID based on their email and password.
     *
     * @param email The email of the employee.
     * @param password The password of the employee.
     * @return The ID of the employee if found.
     * @throws BusinessServiceException If there is an error while fetching the ID from the data layer.
     */
    Long getIdByEmailAndPassword(String email,String password) throws BusinessServiceException;

    /**
     * Retrieves the employee's ID based on their email and password.
     *
     * @param email The email of the employee.
     * @param password The password of the employee.
     * @return The FullName of the employee if found.
     * @throws BusinessServiceException If there is an error while fetching the ID from the data layer.
     */
    String getNameByEmailAndPassword(String email,String password) throws BusinessServiceException;

    /**
     * Updates the password of an employee based on the provided email.
     *
     * @param email The email of the employee whose password is to be updated.
     * @param password The new password to set for the employee.
     * @return {@code true} if the password was successfully updated; {@code false} if no employee with the provided email was found.
     * @throws BusinessServiceException If there is an error during the password update process.
     */
    boolean updateEmployeePassword(String email,String password) throws BusinessServiceException;


    /**
     * Checks if an employee exists with the given email.
     *
     * @param email The email address to check for existence in the database.
     * @return {@code true} if an employee with the specified email exists, {@code false} otherwise.
     * @throws BusinessServiceException If there is an error while checking the email existence.
     */
    boolean isEmailExists(String email) throws BusinessServiceException;

    /**
     * Retrieves an Employee based on the given ID.
     *
     * <p>This method retrieves the Employee object from the database using the provided ID. If the employee is
     * not found or an error occurs while fetching the employee, a {@link BusinessServiceException} is thrown.</p>
     *
     * @param id The ID of the Employee to retrieve.
     * @return The Employee object corresponding to the provided ID.
     * @throws BusinessServiceException If an error occurs during the retrieval process, such as a database or
     *                                   transaction issue.
     */
    Employee getEmployeeeById(Long id) throws BusinessServiceException;

    /**
     * Saves the provided job posting to the database.
     * This method handles the saving of the job posting and ensures that
     * any error encountered during the saving process results in a BusinessServiceException.
     *
     * @param jobPosting The job posting to be saved.
     * @throws BusinessServiceException if there is an error during the save operation,
     *                                   such as a database issue or other unexpected errors.
     */
    void saveJobPostings(JobPosting jobPosting) throws BusinessServiceException;

    /**
     * Retrieves a list of all job postings from the database.
     * This method interacts with the data layer to fetch job postings and may throw an exception if
     * any error occurs during the retrieval process.
     * If an error occurs, it will throw a BusinessServiceException, wrapping any underlying data access
     * or business logic errors.
     *
     * @return A list of all job postings in the database.
     * @throws BusinessServiceException if there is an error during the retrieval process.
     */
    Map<String, Object> getallJobPosting(int page, int size) throws BusinessServiceException;

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
    Employee  updateEmployee(Long id, Employee updatedEmployee) throws BusinessServiceException;

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
    List<JobPosting> getJobPostingsByJobSeekerId(Long id) throws BusinessServiceException;

    /**
     * Retrieves the count of distinct job seekers who have applied for a specific job posting.
     *
     * @param jobPostingId The ID of the job posting for which the applicant count is to be fetched.
     * @return The count of distinct applicants (job seekers) who have applied for the specified job posting.
     * @throws BusinessServiceException If an error occurs while fetching the applicant count, such as a database access error or an unexpected issue.
     */
    Long getApplicantCount(Long jobPostingId) throws BusinessServiceException;

    /**
     *
     * Fetches the list of resumes associated with a specific job posting.
     *
     * @param jobPostingId The ID of the job posting for which resumes are to be retrieved.
     * @return A list of resumes associated with the given job posting ID. If no resumes are found or an error occurs, an empty list is returned.
     * @throws BusinessServiceException If an error occurs during the operation, such as a database access error or any unexpected failure.
     */
    List<Resume> findResumesByJobPostingId(Long jobPostingId) throws BusinessServiceException;

    /**
     * Fetches the list of applications associated with a specific job posting ID.
     *
     * @param id The ID of the job posting for which applications are to be retrieved.
     * @return A list of applications associated with the given job posting ID. If no applications are found, an empty list is returned.
     * @throws BusinessServiceException If an error occurs during the operation, such as a database access error or any unexpected failure.
     */
    List<Applications> getApplicationById(Long id) throws BusinessServiceException;

    /**
     * Updates the application details based on the provided application ID and updated application data.
     *
     * @param id The ID of the application to be updated.
     * @param applications The updated application data that will replace the existing data.
     * @return The updated application object after the update has been applied.
     * @throws BusinessServiceException If there is an error while updating the application, such as a database access error,
     *                                   or if the application with the given ID does not exist.
     */
    Applications updatApplication(Long id, Applications applications) throws BusinessServiceException;


    Applications getStatusByApplicationId(Long applicationId) throws BusinessServiceException;

    /**
     * based on id we are going to delete the Jobposting
     * @param id we pass this id and delete the Jobposting
     * @throws BusinessServiceException it is going to handle the error in the sql
     */
    void deleteJobPostingById(Long id) throws BusinessServiceException;
}
