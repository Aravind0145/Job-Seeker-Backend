package com.Aravind.demo.DaoImplementation;

import com.Aravind.demo.Dao.EmployeeDao;
import com.Aravind.demo.Exception.DataServiceException;
import com.Aravind.demo.Quries.EmployeeQuries;
import com.Aravind.demo.entity.Resume;
import com.Aravind.demo.entity.Applications;
import com.Aravind.demo.entity.JobPosting;
import com.Aravind.demo.entity.Employee;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeDaoImplementation implements EmployeeDao {

    @Autowired
    private SessionFactory sessionFactory;


    /**
     * Saves an employee object to the database.
     *
     * @param employee The employee object to be saved.
     * @throws DataServiceException If there is any error during the save operation.
     */
    @Override
    public void saveEmployees(Employee employee) throws DataServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new DataServiceException("Error saving employee: " + employee.getId(), e); // Throw custom exception with the error message
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    /**
     * Retrieves the role of an employee based on their email and password.
     *
     * @param email The email of the employee.
     * @param password The password of the employee.
     * @return The role of the employee if found.
     * @throws DataServiceException If there is an error during database interaction or if no role is found.
     */
    @Override
    public String getRoleByEmailAndPassword(String email, String password) throws DataServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<String> query = session.createQuery(EmployeeQuries.getRoleByEmailAndPassword, String.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            String role = query.uniqueResult();
            return role;
        } catch (NoResultException e) {
            throw new DataServiceException("No employee found with the given email and password", e);
        } catch (NonUniqueResultException e) {
            throw new DataServiceException("Multiple employees found with the same email and password", e);
        } catch (HibernateException e) {
            throw new DataServiceException("Error executing query for employee role", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    /**
     * Retrieves the ID of an employee based on their email and password.
     *
     * @param email The email of the employee.
     * @param password The password of the employee.
     * @return The ID of the employee if found.
     * @throws DataServiceException If there is an error during database interaction or if no result is found.
     */
    @Override
    public Long getIdByEmailAndPassword(String email, String password) throws DataServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Long> query = session.createQuery(EmployeeQuries.getIdByEmailAndPassword, Long.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            Long employeeId = query.uniqueResult();


            return employeeId;
        } catch (NoResultException e) {
            throw new DataServiceException("No employee found with the given email and password", e);
        } catch (NonUniqueResultException e) {
            throw new DataServiceException("Multiple employees found with the same email and password", e);
        } catch (HibernateException e) {
            throw new DataServiceException("Error executing query to retrieve employee ID", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Retrieves the full name of an employee based on their email and password.

     * @param email The email of the employee.
     * @param password The password of the employee.
     * @return The full name of the employee if found.
     * @throws DataServiceException If there is an error during database interaction or if no result is found.
     */
    @Override
    public String getNameByEmailAndPassword(String email, String password) throws DataServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<String> query = session.createQuery(EmployeeQuries.getFullNameByEmailAndPassowrd, String.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            String fullName = query.uniqueResult();


            return fullName;
        } catch (NoResultException e) {
            throw new DataServiceException("No employee found with the given email and password", e);
        } catch (NonUniqueResultException e) {
            throw new DataServiceException("Multiple employees found with the same email and password", e);
        } catch (HibernateException e) {
            throw new DataServiceException("Error executing query to retrieve employee full name", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Updates the password for an employee based on the provided email.
     *
     * @param email The email of the employee whose password needs to be updated.
     * @param password The new password to be set for the employee.
     * @return {@code true} if the password was successfully updated, {@code false} if no employee with the provided email was found.
     * @throws DataServiceException If there is an error updating the password in the database, including Hibernate errors.
     */
    @Override
    public boolean updateEmployeePassword(String email, String password) throws DataServiceException {
        Session session = sessionFactory.openSession(); // Open a new session
        Transaction transaction = null;  // To manage the transaction

        try {
            transaction = session.beginTransaction();

            Query query = session.createQuery(EmployeeQuries.UpdateEmployeePassword);
            query.setParameter("password", password);  // Use plain password, it will be hashed before storing
            query.setParameter("email", email);

            int result = query.executeUpdate();

            if (result > 0) {
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (HibernateException e) {

            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataServiceException("Error updating password for email: " + email, e); // Wrap in a custom exception
        }  finally {

            session.close();
        }
    }

    /**
     * Checks if an employee exists with the given email.
     *
     * @param email The email address to check for existence in the database.
     * @return {@code true} if an employee with the specified email exists, {@code false} otherwise.
     * @throws DataServiceException If there is an error while querying the database.
     */
    @Override
    public boolean isEmailExists(String email) throws DataServiceException {
        Session session = sessionFactory.openSession();
        try {
            Query<Long> query = session.createQuery(EmployeeQuries.isEmailExits, Long.class);
            query.setParameter("email", email);
            Long count = query.uniqueResult();
            return count > 0;
        } catch (HibernateException e) {

            throw new DataServiceException("Error checking if email exists: " + email, e);  // Wrap Hibernate exception in a custom DataServiceException
        } finally {
            session.close(); // Ensure session is closed
        }
    }

    /**
     * Retrieves an Employee from the database using their ID.
     *
     * <p>This method fetches the Employee object by ID from the database. If the employee is not found,
     * an {@link EntityNotFoundException} is thrown. If any Hibernate-related or database errors occur,
     * a {@link DataServiceException} is thrown.</p>
     *
     * @param id The ID of the employee to retrieve.
     * @return The Employee object associated with the specified ID.
     * @throws DataServiceException If there is an error during the Hibernate operation, such as a database connection issue.
     * @throws EntityNotFoundException If no Employee is found with the given ID.
     */
    @Override
    public Employee getEmployeeeById(Long id) throws DataServiceException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Employee employee = null;

        try {
            transaction = session.beginTransaction();
            employee = session.get(Employee.class, id);

            if (employee == null) {
                throw new EntityNotFoundException("Employee with id " + id + " not found.");
            }

            transaction.commit();
        } catch (HibernateException e) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw new DataServiceException("Error fetching Employee with id " + id, e);
        } catch (EntityNotFoundException e) {

            throw e;
        } finally {
            session.close();
        }

        return employee;
    }


    /**
     * Saves the provided job posting to the database.
     * If there is an error during the saving process, a custom exception is thrown.
     *
     * @param jobposting The job posting to be saved.
     * @throws DataServiceException if there is an error during the save operation.
     */
    @Override
    public void saveJobPostings(JobPosting jobposting) throws DataServiceException {
        Session session = sessionFactory.openSession();  // Open a new session
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();  // Start the transaction
            session.save(jobposting);
            transaction.commit();

        } catch (HibernateException e) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw new DataServiceException("Error saving job posting", e);
        } finally {
            session.close();  // Ensure session is closed to release resources
        }
    }


    /**
     * Retrieves all job postings from the database.
     * This method executes a HQL query to fetch all the job postings and handles various exceptions that may occur.
     * If an error occurs during the process, an appropriate exception will be thrown, and an empty list is returned.
     *
     * @return A list of all job postings in the database, or an empty list if an error occurs.
     * @throws DataServiceException if there is an error during the retrieval process, such as a Hibernate or data access issue.
     */
    @Override
    public Map<String, Object> getallJobPosting(int page, int size) throws DataServiceException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();


            Query<Long> countQuery = session.createQuery("SELECT COUNT(j) FROM JobPosting j", Long.class);
            long totalCount = countQuery.getSingleResult();


            Query<JobPosting> query = session.createQuery(EmployeeQuries.getallJobPostings, JobPosting.class);
            query.setFirstResult((page - 1) * size); // Calculate offset
            query.setMaxResults(size);              // Set max results
            List<JobPosting> paginatedJobPostings = query.getResultList();

            session.getTransaction().commit();


            Map<String, Object> response = new HashMap<>();
            response.put("data", paginatedJobPostings);
            response.put("totalCount", totalCount);

            return response;
        } catch (HibernateException e) {
            throw new DataServiceException("Error while retrieving job postings", e);
        }
    }

    /**
     * Updates an existing Employee record in the database.
     *
     * @param id The ID of the Employee to update.
     * @param updatedEmployee The updated Employee object containing new data.
     * @return The updated Employee object.
     * @throws DataServiceException If there is an error in the database or the employee is not found.
     */

    @Override
    public Employee updateEmployee(Long id, Employee updatedEmployee) throws DataServiceException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Employee updatedEntity = null;

        try {
            transaction = session.beginTransaction();

            Query query = session.createQuery(EmployeeQuries.UpdateEmployee);
            query.setParameter("companyName", updatedEmployee.getCompanyName());
            query.setParameter("websiteUrl", updatedEmployee.getWebsiteUrl());
            query.setParameter("fullName", updatedEmployee.getFullName());
            query.setParameter("email", updatedEmployee.getOfficialEmail());
            query.setParameter("mobileNumber", updatedEmployee.getMobileNumber());
            query.setParameter("profilePhoto", updatedEmployee.getProfilePhoto());
            query.setParameter("designation", updatedEmployee.getDesignation());
            query.setParameter("password", updatedEmployee.getPassword()); // Ensure password is hashed if necessary
            query.setParameter("role", updatedEmployee.getRole());
            query.setParameter("id", id);


            int result = query.executeUpdate();

            if (result > 0) {
                transaction.commit();

                updatedEntity = session.get(Employee.class, id);
            } else {
                throw new EntityNotFoundException("Employee with id " + id + " not found.");
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataServiceException("Error updating Employee with id " + id, e); // Wrap HibernateException into a custom DataServiceException
        } catch (EntityNotFoundException e) {

            throw new DataServiceException("Employee with id " + id + " not found for update", e); // Wrap EntityNotFoundException into a custom DataServiceException
        } finally {
            session.close();
        }

        return updatedEntity;
    }

    /**
     * Fetches all job postings associated with a specific job seeker based on their ID.
     *
     * This method retrieves a list of `JobPosting` entities from the database where
     * the `jobSeekerId` matches the provided ID. If no job postings are found, it returns
     * an empty list. If an error occurs during the query, a `DataServiceException` is thrown.
     *
     * @param jobSeekerId The ID of the job seeker whose job postings need to be fetched.
     * @return A list of `JobPosting` objects associated with the provided job seeker ID.
     * @throws DataServiceException If there is an error during the database query or transaction.
     */
    @Override
    public List<JobPosting> getJobPostingsByJobSeekerId(Long jobSeekerId) throws DataServiceException {
        // Open a session from the sessionFactory
        Session session = sessionFactory.openSession();

        try {

            session.beginTransaction();


            Query<JobPosting> query = session.createQuery(EmployeeQuries.getallJobPostingsByJobseekerid, JobPosting.class);
            query.setParameter("jobSeekerId", jobSeekerId);


            List<JobPosting> jobPostingsList = query.getResultList();


            session.getTransaction().commit();


            return jobPostingsList;
        } catch (HibernateException e) {

            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }

            throw new DataServiceException("Error fetching job postings for job seeker ID: " + jobSeekerId, e);
        }  finally {

            session.close();
        }
    }

    /**
     * Retrieves the count of distinct job seekers who have applied for a specific job posting.
     *
     * @param jobPostingId The ID of the job posting for which the applicant count is to be fetched.
     * @return The count of distinct applicants (job seekers) who have applied for the specified job posting.
     * @throws DataServiceException If an error occurs while querying the database, including any Hibernate exceptions or other unexpected issues.
     */
    @Override
    public Long getApplicantCount(Long jobPostingId) throws DataServiceException {

        Session session = sessionFactory.openSession();

        try {

            session.beginTransaction();


            Query<Long> query = session.createQuery(EmployeeQuries.getcountApplications, Long.class);


            query.setParameter("jobPostingId", jobPostingId);


            Long applicantCount = query.uniqueResult();


            session.getTransaction().commit();


            return applicantCount;

        } catch (HibernateException e) {

            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }

            throw new DataServiceException("Error retrieving applicant count for job posting ID: " + jobPostingId, e);
        } finally {

            session.close();
        }
    }


    /**
     * Retrieves the list of resumes associated with a specific job posting.
     *
     * @param jobPostingId The ID of the job posting for which resumes are to be fetched.
     * @return A list of resumes associated with the given job posting ID.
     * @throws DataServiceException If an error occurs while fetching the resumes, such as a database access error or a transaction issue.
     */
    @Override
    public List<Resume> findResumesByJobPostingId(Long jobPostingId) throws DataServiceException {
        Session session = sessionFactory.openSession();
        List<Resume> resumes = Collections.emptyList();

        try {

            session.beginTransaction();


            Query<Resume> query = session.createQuery(EmployeeQuries.findresumebyjobjobpostingid, Resume.class);


            query.setParameter("jobPostingId", jobPostingId);

            resumes = query.getResultList();


            session.getTransaction().commit();

        } catch (HibernateException e) {

            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new DataServiceException("Error while fetching resumes for JobPosting ID: " + jobPostingId, e);
        } finally {

            session.close();
        }

        return resumes;
    }

    /**
     * Fetches the list of applications associated with a specific job posting ID.
     *
     * @param jobPostingId The ID of the job posting for which applications are to be retrieved.
     * @return A list of applications associated with the given job posting ID. If no applications are found, an empty list is returned.
     * @throws DataServiceException If an error occurs during the operation, such as a database access error or any unexpected failure.
     */
    @Override
    public List<Applications> getApplicationById(Long jobPostingId) throws DataServiceException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Applications> applications = null;

        try {
            transaction = session.beginTransaction();


            applications = session.createQuery(EmployeeQuries.getApplicationById, Applications.class)
                    .setParameter("jobPostingId", jobPostingId)
                    .getResultList();

            transaction.commit();
        } catch (HibernateException e) {

            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataServiceException("Error fetching applications for Job Posting with ID: " + jobPostingId, e);
        } finally {
            session.close();
        }

        return applications;
    }


    /**
     * Updates the application details based on the provided application ID and updated application data.
     *
     * @param id The ID of the application to be updated.
     * @param updatedApplication The updated application data that will replace the existing data.
     * @return The updated application object after the update has been applied.
     * @throws DataServiceException If there is an error while updating the application, such as a database access error,
     *                               or if the application with the given ID does not exist.
     */
    @Override
    public Applications updatApplication(Long id, Applications updatedApplication) throws DataServiceException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Applications updatedEntity = null;

        try {
            transaction = session.beginTransaction();


            Query query = session.createQuery(EmployeeQuries.UpdateApplication);
            query.setParameter("jobPosting", updatedApplication.getJobPosting());
            query.setParameter("jobSeeker", updatedApplication.getJobSeeker());
            query.setParameter("resume", updatedApplication.getResume());
            query.setParameter("status", updatedApplication.getStatus());
            query.setParameter("applicationId", id); // Use applicationId as the filter


            int result = query.executeUpdate();

            if (result > 0) {
                transaction.commit();

                updatedEntity = session.get(Applications.class, id);
            } else {
                throw new EntityNotFoundException("Application with applicationId " + id + " not found.");
            }
        } catch (HibernateException e) {

            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataServiceException("Error updating Application with id " + id, e); // Wrap HibernateException into a DataServiceException
        } catch (EntityNotFoundException e) {

            throw new DataServiceException("Application with id " + id + " not found for update", e); // Wrap EntityNotFoundException into a DataServiceException
        } finally {
            session.close();
        }

        return updatedEntity;
    }

    @Override
    public Applications getStatusByApplicationId(Long applicationId) throws DataServiceException{
        Session session = sessionFactory.openSession(); // Open a new session
        Transaction transaction = null;
        Applications applications = null;

        try {
            transaction = session.beginTransaction();
            applications = session.get(Applications.class, applicationId);  // Fetch Employee by ID

            if (applications == null) {
                throw new EntityNotFoundException("Employee with id " + applicationId + " not found.");
            }

            transaction.commit();  // Commit the transaction
        } catch (HibernateException e) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw new DataServiceException("Error fetching Employee with id " + applicationId, e);
        } catch (EntityNotFoundException e) {

            throw e;
        } finally {
            session.close();
        }

        return applications;

    }

    /**
     *  based on the Jobposting we can delete the Jobposting
     * @param jobPostingId jobposting id based on this we are goin to delete the jobposting
     * @throws DataServiceException it will help use to handle the error
     */
    @Override
    public void deleteJobPostingById(Long jobPostingId) throws DataServiceException {
        // Open a Hibernate session
        Session session = sessionFactory.openSession();

        try {

            session.beginTransaction();


            Query<?> deleteApplicationsQuery = session.createQuery(EmployeeQuries.DleteapplicationId);
            deleteApplicationsQuery.setParameter("jobPostingId", jobPostingId);
            deleteApplicationsQuery.executeUpdate();


            Query<?> deleteJobPostingQuery = session.createQuery(EmployeeQuries.DeleteJobposting);
            deleteJobPostingQuery.setParameter("jobPostingId", jobPostingId);
            int result = deleteJobPostingQuery.executeUpdate();

            if (result == 0) {
                throw new EntityNotFoundException("JobPosting with ID " + jobPostingId + " not found.");
            }


            session.getTransaction().commit();
        } catch (HibernateException e) {

            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new DataServiceException("Error deleting JobPosting with ID: " + jobPostingId, e);
        } finally {

            session.close();
        }
    }




}
