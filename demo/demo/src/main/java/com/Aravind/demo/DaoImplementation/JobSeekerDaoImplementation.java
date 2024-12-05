package com.Aravind.demo.DaoImplementation;

import com.Aravind.demo.Dao.JobSeekerDao;
import com.Aravind.demo.Exception.DataServiceException;
import com.Aravind.demo.Quries.EmployeeQuries;
import com.Aravind.demo.Quries.JobSeekerQueries;
import com.Aravind.demo.entity.Applications;
import com.Aravind.demo.entity.JobPosting;
import com.Aravind.demo.entity.JobSeeker;
import com.Aravind.demo.entity.Resume;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerDaoImplementation implements JobSeekerDao {

    @Autowired
    private SessionFactory sessionFactory;


    /**
     * Saves the provided JobSeeker entity to the database.
     * This method opens a Hibernate session, starts a transaction,
     * saves the JobSeeker object, and commits the transaction.
     * If an error occurs during the process, the transaction is rolled back,
     * and a DataServiceException is thrown.
     *
     * @param jobSeeker The JobSeeker object to be saved.
     *                  This should not be null and must contain valid data for
     *                  all the fields required by the JobSeeker entity.
     *
     * @throws DataServiceException If an error occurs while saving the JobSeeker in the database.
     *                              This exception wraps any HibernateException that occurs during
     *                              the session or transaction process, such as database connection issues,
     *                              constraint violations, or any other data access related errors.
     *
     */
    @Override
    public void saveJobseeker(JobSeeker jobSeeker) throws DataServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession();  // Open a session
            session.beginTransaction();  // Begin the transaction
            session.save(jobSeeker);  // Save the jobSeeker entity
            session.getTransaction().commit();  // Commit the transaction

        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new DataServiceException("Error saving JobSeeker in the database", e);  // Wrap and throw a custom exception

        } finally {
            if (session != null) {
                session.close();  // Close the session
            }
        }

    }

    /**
     * Retrieves the role of a JobSeeker based on the provided email and password.
     * This method executes a Hibernate query to fetch the role associated with the given email and password.
     * If no results are found, or if multiple results are found, a custom exception is thrown.
     *
     * @param email The email of the JobSeeker. This should be a valid email associated with a registered JobSeeker.
     * @param password The password of the JobSeeker. This should be the correct password associated with the email.
     *
     * @return A string representing the role of the JobSeeker (e.g., "Admin", "User", etc.).
     *
     * @throws DataServiceException If an error occurs during query execution or if unexpected results are found.
     *                               The exception may be caused by:
     *                               <ul>
     *                                   <li>No results found for the given email and password (NoResultException).</li>
     *                                   <li>Multiple results found for the given email and password (NonUniqueResultException).</li>
     *                                   <li>General Hibernate exceptions during query execution (HibernateException).</li>
     *                               </ul>
     *
     */
    @Override
    public String getRoleByEmailAndPassword(String email, String password) throws DataServiceException{
        Session session = sessionFactory.openSession();
        try {

            Query<String> query = session.createQuery(JobSeekerQueries.GET_ROLE_BY_EMAIL_AND_PASSWORD, String.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.uniqueResult();
        } catch (NoResultException e) {
            throw new DataServiceException("No JobSeeker found with the given email and password", e);
        } catch (NonUniqueResultException e) {
            throw new DataServiceException("Multiple JobSeekers found with the same email and password", e);
        } catch (HibernateException e) {
            throw new DataServiceException("Error executing query for JobSeeker role", e);
        } finally {
            session.close();
        }
    }


    /**
     * Retrieves the unique ID of a JobSeeker based on the provided email and password.
     * This method executes a Hibernate query to fetch the JobSeeker ID associated with the given email and password.
     * If no result is found, or if multiple results are found, a custom exception is thrown.
     *
     * @param email The email of the JobSeeker. This should be a valid email associated with a registered JobSeeker.
     * @param password The password of the JobSeeker. This should be the correct password associated with the email.
     *
     * @return The ID of the JobSeeker as a {@code Long}. If the email and password are valid, the corresponding JobSeeker ID is returned.
     *
     * @throws DataServiceException If an error occurs during query execution or if unexpected results are found.
     *                               The exception may be caused by:
     *                               <ul>
     *                                   <li>No results found for the given email and password (NoResultException).</li>
     *                                   <li>Multiple results found for the given email and password (NonUniqueResultException).</li>
     *                                   <li>General Hibernate exceptions during query execution (HibernateException).</li>
     *                               </ul>
     *
     */
    @Override
    public Long getIdByEmailAndPassword(String email, String password) throws DataServiceException {
        Session session = null;
        Long result = null;

        try {
            session = sessionFactory.openSession(); // Get current session
            Query<Long> query = session.createQuery(JobSeekerQueries.GET_ID_BY_EMAIL_AND_PASSWORD, Long.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            result = query.uniqueResult();

            if (result == null) {
                throw new NoResultException("No JobSeeker found for email: " + email); // Optional, since uniqueResult returns null when no result
            }
        } catch (NoResultException e) {
            throw new DataServiceException("No JobSeeker found for the provided email and password.", e);

        } catch (NonUniqueResultException e) {
            throw new DataServiceException("Multiple JobSeekers found for the same email and password.", e);

        } catch (HibernateException e) {
            throw new DataServiceException("Error while querying the JobSeeker.", e);
        }  finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    /**
     * Retrieves the full name of a JobSeeker based on the provided email and password.
     * This method executes a Hibernate query to fetch the JobSeeker's full name associated with the given email and password.
     * If no result is found, or if multiple results are found, a custom exception will be thrown.
     *
     * @param email The email of the JobSeeker. This should be a valid email associated with a registered JobSeeker.
     * @param password The password of the JobSeeker. This should be the correct password associated with the email.
     *
     * @return The full name of the JobSeeker as a {@code String}. If the email and password are valid, the corresponding JobSeeker's full name is returned.
     *
     * @throws DataServiceException If an error occurs during query execution or if unexpected results are found.
     *                               The exception may be caused by:
     *                               <ul>
     *                                   <li>No results found for the given email and password (NoResultException).</li>
     *                                   <li>Multiple results found for the given email and password (NonUniqueResultException).</li>
     *                                   <li>General Hibernate exceptions during query execution (HibernateException).</li>
     *                               </ul>
     */
    @Override
    public String getNameByEmailAndPassword(String email, String password) throws DataServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession(); // Open a session
            Query<String> query = session.createQuery(JobSeekerQueries.GET_FULLNAME_BY_EMAIL_AND_PASSWORD, String.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            // Execute query and get the unique result
            return (String) query.uniqueResult();

        } catch (NoResultException e) {
            // No JobSeeker found with the given email and password
            throw new DataServiceException("No JobSeeker found for the provided email and password.", e);

        } catch (NonUniqueResultException e) {
            // Multiple JobSeekers found with the same email and password (this is unexpected behavior)
            throw new DataServiceException("Multiple JobSeekers found for the provided email and password.", e);

        } catch (HibernateException e) {
            // General Hibernate-related exceptions (e.g., query issues, database errors)
            throw new DataServiceException("Error executing query to fetch JobSeeker full name.", e);

        } finally {
            // Ensure the session is closed to release resources
            if (session != null) {
                session.close();
            }
        }
    }


    /**
     * Saves a JobSeeker's resume to the database.
     * This method opens a Hibernate session, begins a transaction, and saves the provided {@link Resume} entity.
     * If any errors occur during the save operation, the transaction is rolled back and a custom exception is thrown.
     *
     * @param resume The {@link Resume} object to be saved. This object must contain valid data for the fields defined in the {@link Resume} entity.
     *               The resume should be properly populated before passing it to this method.
     *
     * @throws DataServiceException If an error occurs during the save operation. This exception is thrown for the following reasons:
     * <ul>
     *     <li>If a Hibernate exception occurs during the transaction (e.g., database issues, constraint violations, etc.).</li>
     *     <li>If the transaction cannot be committed or rolled back due to an unexpected error.</li>
     * </ul>
     * The {@link DataServiceException} is a custom exception that wraps the underlying Hibernate exception.
     */
    @Override
    public void saveJobResume(Resume resume) throws DataServiceException{
        Session session = null;
        try {
            session = sessionFactory.openSession(); // Open a session
            session.beginTransaction(); // Begin the transaction
            // Save the resume entity
            session.save(resume);
            // Commit the transaction
            session.getTransaction().commit();

        } catch (HibernateException e) {
            // If an exception occurs, roll back the transaction and throw a custom exception
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            // Wrap the Hibernate exception in a custom DataServiceException
            throw new DataServiceException("Error saving Job Resume in the database", e);

        } finally {
            // Ensure the session is closed to release resources
            if (session != null) {
                session.close();
            }
        }


    }

    @Override
    public boolean checkResumeExistence(Long jobseekerId) throws DataServiceException{
        Session session = sessionFactory.openSession();
        try {
            // Create the HQL query to count resumes associated with the jobseeker
            Query<Long> query = session.createQuery("SELECT count(r) FROM Resume r WHERE r.jobSeeker.id = :jobSeekerId", Long.class);
            query.setParameter("jobSeekerId", jobseekerId);

            // Execute the query and get the count of resumes
            Long count = query.getSingleResult();

            // If count is greater than 0, resume exists
            return count > 0;
        } catch (HibernateException e) {
            // Log the exception (optional)
            System.err.println("Hibernate exception occurred while checking resume existence: " + e.getMessage());
            // Throw a custom DataServiceException with a specific message
            throw new DataServiceException("Error occurred while checking resume existence for jobseeker ID: " + jobseekerId, e);
        } finally {
            // Ensure session is closed after use
            session.close();
        }
    }



    /**
     * Retrieves a JobSeeker entity by its unique ID.
     * This method queries the database to fetch a JobSeeker using the provided ID.
     * If the JobSeeker with the given ID is not found, {@code null} is returned.
     * In case of any database errors or exceptions, a custom {@link DataServiceException} is thrown.
     *
     * @param id The unique identifier of the JobSeeker to retrieve. This should be a valid, existing ID in the database.
     *           It cannot be {@code null}.
     *
     * @return The JobSeeker object associated with the provided ID, or {@code null} if no such JobSeeker exists.
     *
     * @throws DataServiceException If there is an error during the Hibernate session or transaction, or if there are issues
     *                               with retrieving the JobSeeker entity. Possible causes include:
     *                               <ul>
     *                                   <li>Database connectivity issues</li>
     *                                   <li>Transaction failures</li>
     *                                   <li>Other Hibernate-specific errors (e.g., issues in session handling)</li>
     *                               </ul>
     */
    @Override
    public JobSeeker getJobSeekerId(Long id) throws DataServiceException{
        Session session = null;
        JobSeeker jobSeeker = null;

        try {
            session = sessionFactory.openSession();  // Open session
            session.beginTransaction();  // Begin transaction

            // Fetch JobSeeker by ID
            jobSeeker = session.get(JobSeeker.class, id);

            // Commit the transaction
            session.getTransaction().commit();
        } catch (HibernateException e) {
            // If there is an issue, rollback the transaction
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            // Wrap Hibernate exception in a custom DataServiceException
            throw new DataServiceException("Error retrieving JobSeeker by ID", e);
        } finally {
            // Close session to release resources
            if (session != null) {
                session.close();
            }
        }

        return jobSeeker;
    }

    /**
     * Retrieves the resume of a JobSeeker based on the provided JobSeeker ID.
     * This method executes a Hibernate query to fetch the resume associated with the given JobSeeker ID.
     * If an error occurs during the query execution, a custom exception will be thrown.
     *
     * @param jobseekerId The ID of the JobSeeker whose resume is being fetched. This ID must exist in the database
     *                    and correspond to a valid JobSeeker entity.
     *
     * @return The {@link Resume} object associated with the provided JobSeeker ID, or {@code null} if no resume is found.
     *
     * @throws DataServiceException If an error occurs during the execution of the query or if the resume cannot be retrieved.
     *                               This exception may be thrown in the following cases:
     *                               <ul>
     *                                   <li>Database connectivity issues or transaction failures (e.g., HibernateException).</li>
     *                                   <li>Any unexpected error that occurs while querying the resume for the JobSeeker.</li>
     *                               </ul>
     */

    @Override
    public Resume getResumeByJobseekerId(Long jobseekerId) throws DataServiceException {
        Session session = null;
        Resume resume = null;
        try {
            session = sessionFactory.openSession(); // Get current session
            Query<Resume> query = session.createQuery(JobSeekerQueries.GET_RESUME_BY_JOBSEEKERID, Resume.class);
            query.setParameter("jobseekerId", jobseekerId);
            // Retrieve the unique result, or null if no result is found
            resume = query.uniqueResult();
        } catch (HibernateException e) {
            // If an exception occurs, throw a custom exception to notify of the error
            throw new DataServiceException("Error retrieving Resume for JobSeeker with ID: " + jobseekerId, e);
        } finally {
            // Ensure the session is closed to release resources
            if (session != null) {
                session.close();
            }
        }
        return resume;
    }

    /**
     * Updates the password for a JobSeeker with the given email.
     *
     * @param email The email address of the JobSeeker whose password needs to be updated.
     * @param password The new password to set for the JobSeeker. The password should be hashed before passing it to this method.
     * @return A boolean indicating whether the password was successfully updated.
     * @throws DataServiceException If there is an error
     *              while updating the password, including issues with the database or
     *              if the JobSeeker with the provided email is not found.
     */
    @Override
    public boolean updateJobSeekerPassword(String email, String password) throws DataServiceException {
        Session session = null;
        Transaction transaction = null;  // To manage the transaction
        boolean isUpdated = false;

        try {
            session = sessionFactory.openSession(); // Open a new session
            transaction = session.beginTransaction(); // Start the transaction

            // HQL query for updating the JobSeeker password
            Query query = session.createQuery(JobSeekerQueries.UPDATE_JOBSEEKER_PASSWORD);
            query.setParameter("password", password);  // Use plain password, it should be hashed before storing
            query.setParameter("email", email);

            // Execute the update
            int result = query.executeUpdate();

            if (result > 0) {
                transaction.commit(); // Commit the transaction if the update is successful
                isUpdated = true;
            } else {
                throw new EntityNotFoundException("No JobSeeker found with the provided email: " + email);
            }
        } catch (EntityNotFoundException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataServiceException("JobSeeker not found with email: " + email, e);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of an error
            }
            throw new DataServiceException("Error updating JobSeeker password for email: " + email, e);

        } finally {
            // Ensure the session is closed to release resources
            if (session != null) {
                session.close();
            }
        }

        return isUpdated; // Return whether the update was successful
    }

    /**
     * Checks if a JobSeeker exists with the given email.
     *
     * @param email The email of the JobSeeker to check for existence.
     * @return true if a JobSeeker with the given email exists, false otherwise.
     * @throws DataServiceException If an error occurs while querying the database.
     */
    public boolean isEmailExists(String email) throws DataServiceException {
        Session session = null;
        boolean exists = false;

        try {
            session = sessionFactory.openSession(); // Open a session
            Query<Long> query = session.createQuery(JobSeekerQueries.CHECK_EMAIL, Long.class);
            query.setParameter("email", email);

            Long count = query.uniqueResult();
            exists = count > 0; // If count is greater than 0, the email exists

        } catch (HibernateException e) {
            // Rollback transaction and handle the exception if any database error occurs
            throw new DataServiceException("Error checking if email exists in the database: " + email, e);
        } finally {
            // Ensure session is closed to release resources
            if (session != null) {
                session.close();
            }
        }

        return exists; // Return whether the email exists or not
    }

    /**
     * Submits an application by saving it to the database.
     * This method opens a session, begins a transaction, and attempts to save the
     * provided application object. If the operation is successful, the transaction
     * is committed. In case of an error, the transaction is rolled back, and a
     * custom DataServiceException is thrown.
     * @param applications The application object to be submitted.
     * @throws DataServiceException If an error occurs while saving the application
     *         or during transaction management.
     */
  /*  @Override
    public void submitApplication(Applications applications) throws DataServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession(); // Open session
            session.beginTransaction(); // Begin transaction
            session.save(applications); // Save the application
            session.getTransaction().commit(); // Commit the transaction
        } catch (HibernateException e) {
            // If an exception occurs, roll back the transaction and throw a custom exception
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            // Wrap the Hibernate exception in a custom DataServiceException
            throw new DataServiceException("Error submitting application", e);
        } finally {
            // Ensure the session is closed to release resources
            if (session != null) {
                session.close();
            }
        }
    }
*/
@Override
    public void submitApplication(Applications applications) throws DataServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession(); // Open session
            session.beginTransaction(); // Begin transaction

            // Check if job seeker has already applied for the same job posting
            Query<Applications> query = session.createQuery(EmployeeQuries.submitapplication, Applications.class
            );
            query.setParameter("jobSeekerId", applications.getJobSeeker().getId());
            query.setParameter("jobPostingId", applications.getJobPosting().getId());

            Applications existingApplication = query.uniqueResult();
            if (existingApplication != null) {
                throw new DataServiceException("You have already applied for this job.");
            }

            session.save(applications); // Save the application
            session.getTransaction().commit(); // Commit the transaction
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new DataServiceException("Error submitting application", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    /**
     * Retrieves a JobPosting by its ID from the database.
     *
     * @param jobPostingId The ID of the job posting to retrieve.
     * @return The JobPosting object corresponding to the provided ID, or null if not found.
     * @throws DataServiceException If an error occurs while fetching
     *              the JobPosting from the database.
     */
    @Override
    public JobPosting getJobPostingById(long jobPostingId) throws DataServiceException {
        Session session = null;
        JobPosting jobPosting = null;

        try {
            session = sessionFactory.openSession();  // Open session
            session.beginTransaction();  // Begin transaction

            // Fetch JobPosting by ID
            jobPosting = session.get(JobPosting.class, jobPostingId);

            // Commit transaction if the fetch was successful
            session.getTransaction().commit();

        } catch (HibernateException e) {
            // If an exception occurs, roll back the transaction
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            // Throw a custom DataServiceException with the Hibernate error message
            throw new DataServiceException("Error retrieving JobPosting with ID: " + jobPostingId, e);

        } finally {
            // Ensure the session is closed to release resources
            if (session != null) {
                session.close();
            }
        }

        return jobPosting;
    }

    /**
     *  @param resumeId The ID of the resume to retrieve.
     *  @return The Resume object corresponding to the provided ID, or null if not found.
     *   @throws DataServiceException If an error occurs while fetching the Resume from the database.
     */
    @Override
    public Resume getResumeById(Long resumeId) throws DataServiceException {
        Session session = null;
        Resume resume = null;

        try {
            session = sessionFactory.openSession();  // Open session
            session.beginTransaction();  // Begin transaction

            // Fetch Resume by ID
            resume = session.get(Resume.class, resumeId);

            // Commit transaction if the fetch was successful
            session.getTransaction().commit();

        } catch (HibernateException e) {
            // If an exception occurs, roll back the transaction
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            // Throw a custom DataServiceException with the Hibernate error message
            throw new DataServiceException("Error retrieving Resume with ID: " + resumeId, e);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return resume;
    }

    /**
     * Updates the details of an existing JobSeeker in the database.
     *
     * This method performs an update operation on the JobSeeker entity identified by the provided ID.
     * The fields in the JobSeeker entity will be updated with the values provided in the
     * `updatedJobSeeker` object. After updating, the updated entity is fetched and returned.
     *
     * @param id The unique identifier of the JobSeeker to be updated.
     * @param updatedJobSeeker A {@link JobSeeker} object containing the updated details.
     * @return The updated {@link JobSeeker} entity from the database.
     * @throws DataServiceException If the JobSeeker with the given ID is not found, or an error occurs
     *         during the update operation.
     */

    @Override
    public JobSeeker updateJobSeeker(Long id, JobSeeker updatedJobSeeker) throws DataServiceException {
        Session session = sessionFactory.openSession(); // Open a new session
        Transaction transaction = session.beginTransaction(); // Start the transaction
        JobSeeker updatedEntity = null;

        try {
            // HQL query for updating the JobSeeker
            Query query = session.createQuery(JobSeekerQueries.UPDATE_JOBSEEKER);
            query.setParameter("fullName", updatedJobSeeker.getFullName());
            query.setParameter("email", updatedJobSeeker.getEmail());
            query.setParameter("password", updatedJobSeeker.getPassword());
            query.setParameter("phone", updatedJobSeeker.getPhone());
            query.setParameter("workStatus", updatedJobSeeker.getWorkStatus());
            query.setParameter("promotions", updatedJobSeeker.isPromotions());
            query.setParameter("id", id);

            // Execute update and check results
            int result = query.executeUpdate();

            if (result > 0) {
                transaction.commit(); // Commit the transaction if the update is successful
                updatedEntity = session.get(JobSeeker.class, id); // Fetch the updated entity
            } else {
                throw new EntityNotFoundException("JobSeeker with id " + id + " not found.");
            }

        } catch (EntityNotFoundException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataServiceException("JobSeeker not found with id: " + id, e); // Wrap EntityNotFoundException in DataServiceException

        } finally {
            session.close(); // Ensure the session is closed
        }

        return updatedEntity; // Return the updated JobSeeker object
    }


    /**
     * Retrieves a list of job applications associated with a specific JobSeeker.
     *
     * @param jobSeekerId the ID of the JobSeeker whose applications are to be retrieved.
     * @return a list of {@link Applications} objects associated with the given JobSeeker ID.
     * @throws DataServiceException if there is an error while fetching the applications from the database.

     */
    @Override
    public List<Applications> getApplicationsByJobSeeker(Long jobSeekerId) throws DataServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession(); // Open a session
            Query<Applications> query = session.createQuery(JobSeekerQueries.GET_APPLICATION_BY_JOBSEEKER, Applications.class);
            query.setParameter("jobSeekerId", jobSeekerId);
            return query.list(); // Return the list of applications
        } catch (HibernateException e) {
            // Wrap and rethrow Hibernate exceptions as DataServiceException
            throw new DataServiceException("Error fetching applications for JobSeeker with ID: " + jobSeekerId, e);
        } finally {
            if (session != null) {
                session.close(); // Ensure the session is closed
            }
        }
    }

    /**
     * Withdraws an application submitted by a job seeker.
     *
     * @param jobSeekerId The ID of the job seeker
     * @param applicationId The ID of the application to withdraw
     * @return true if the application was successfully withdrawn, false if the application was not found
     * @throws DataServiceException if there is an error during the withdrawal process
     */

    @Override
    public boolean withdrawApplication(long jobSeekerId, Long applicationId) throws DataServiceException{
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Get the application to be deleted
            Applications application = session.createQuery(JobSeekerQueries.WITHDRAW_APPLICATION, Applications.class)
                    .setParameter("applicationId", applicationId)
                    .setParameter("jobSeekerId", jobSeekerId)
                    .uniqueResult();

            if (application != null) {
                session.delete(application); // Delete the application
                transaction.commit(); // Commit the transaction
                return true;
            } else {
                return false; // Application not found
            }

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of error
            }
            // Wrap the Hibernate exception in a custom DataServiceException
            throw new DataServiceException("Error withdrawing application with ID: " + applicationId + " for JobSeeker with ID: " + jobSeekerId, e);
        }
        finally {
            session.close(); // Close the session
        }
    }

    /**
     * Searches for job postings based on the given parameters (job title, location, and experience).
     * If any parameter is null or empty, it is ignored in the search.
     *
     * @param jobTitle The title of the job to search for.
     * @param location The location where the job is located.
     * @param experience The required experience for the job.
     * @return A list of JobPosting objects matching the search criteria.
     * @throws DataServiceException If an error occurs while searching for job postings.
     */
    public List<JobPosting> searchJobs(String jobTitle, String location, String experience) throws DataServiceException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<JobPosting> jobPostings = null;

        try {
            transaction = session.beginTransaction();
            var query = session.createQuery(JobSeekerQueries.SEARCH_JOBS, JobPosting.class);
            query.setParameter("jobTitle", jobTitle != null && !jobTitle.isEmpty() ? "%" + jobTitle + "%" : null);
            query.setParameter("location", location != null && !location.isEmpty() ? "%" + location + "%" : null);
            query.setParameter("experience", experience != null && !experience.isEmpty() ? "%" + experience + "%" : null);

            jobPostings = query.list();

            transaction.commit(); // Commit transaction
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback transaction in case of error
            }
            // Throw custom exception when Hibernate-related error occurs
            throw new DataServiceException("Error occurred while searching for job postings due to Hibernate issue", e);
        } finally {
            session.close(); // Close session
        }

        return jobPostings;
    }
    /**
     * Updates the Resume entity with the provided details and the given ID.
     *
     * @param resume The updated Resume object.
     * @param id The ID of the Resume to be updated.
     * @return The updated Resume object.
     * @throws DataServiceException If an error occurs while updating the Resume.
     */
    @Override
    public Resume updatedResume(Resume resume, Long id) throws DataServiceException {
        Session session = sessionFactory.openSession(); // Open a new session
        Transaction transaction = null; // To manage the transaction
        Resume updatedEntity = null;

        try {
            transaction = session.beginTransaction();



            Query query = session.createQuery(JobSeekerQueries.UPDATE_RESUME);
            query.setParameter("headline", resume.getHeadline());
            query.setParameter("firstName", resume.getFirstName());
            query.setParameter("middleName", resume.getMiddleName());
            query.setParameter("lastName", resume.getLastName());
            query.setParameter("email", resume.getEmail());
            query.setParameter("phoneNumber", resume.getPhoneNumber());
            query.setParameter("dob", resume.getDob());
            query.setParameter("languages", resume.getLanguages());
            query.setParameter("linkedinurl", resume.getLinkedinurl());
            query.setParameter("permanentAddress", resume.getPermanentAddress());
            query.setParameter("currentAddress", resume.getCurrentAddress());
            query.setParameter("xth", resume.getXth());
            query.setParameter("xthYear", resume.getXthYear());
            query.setParameter("xii", resume.getXii());
            query.setParameter("xiiYear", resume.getXiiYear());
            query.setParameter("graduation", resume.getGraduation());
            query.setParameter("graduationYear", resume.getGraduationYear());
            query.setParameter("pg", resume.getPg());
            query.setParameter("pgStatus", resume.getPgStatus());
            query.setParameter("skills",resume.getSkills());
            query.setParameter("projectTitle", resume.getProjectTitle());
            query.setParameter("projectDescription", resume.getProjectDescription());
            query.setParameter("certificateName", resume.getCertificateName());
            query.setParameter("certificateDescription", resume.getCertificateDescription());
            query.setParameter("companyName", resume.getCompanyName());
            query.setParameter("startDate", resume.getStartDate());
            query.setParameter("endDate", resume.getEndDate());
            query.setParameter("jobTitle", resume.getJobTitle());
            query.setParameter("jobDescription", resume.getJobDescription());
            query.setParameter("id", id);

            // Execute the update
            int result = query.executeUpdate();

            if (result > 0) {
                transaction.commit(); // Commit the transaction if the update is successful
                // Fetch the updated Resume object
                updatedEntity = session.get(Resume.class, id);
            } else {
                throw new EntityNotFoundException("Resume with id " + id + " not found.");
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of an error
            }
            // Wrap HibernateException in a custom DataServiceException
            throw new DataServiceException("Error updating Resume with id: " + id, e);
        } finally {
            session.close(); // Close the session
        }

        return updatedEntity; // Return the updated Resume object
    }


}






