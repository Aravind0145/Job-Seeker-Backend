package com.Aravind.demo.ServiceImplementaion;

import com.Aravind.demo.Dao.EmployeeDao;
import com.Aravind.demo.Exception.BusinessServiceException;
import com.Aravind.demo.Exception.DataServiceException;
import com.Aravind.demo.Message.MessageHandle;
import com.Aravind.demo.Service.EmailService;
import com.Aravind.demo.Service.EmployeeService;
import com.Aravind.demo.Service.JobService;
import com.Aravind.demo.entity.Applications;
import com.Aravind.demo.entity.Employee;
import com.Aravind.demo.entity.JobSeeker;
import com.Aravind.demo.entity.JobPosting;
import com.Aravind.demo.entity.Resume;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    private final EmployeeDao employeeDAO;

    @Autowired
    private EmailService emailService;
    @Autowired
    private JobService jobService;

    @Autowired
    public EmployeeServiceImplementation(EmployeeDao employeeDAO) {
        this.employeeDAO = employeeDAO;
    }


    @Override
    public void registerEmployee(Employee employee) throws BusinessServiceException, MessagingException {
        try {
            employeeDAO.saveEmployees(employee);

            String subject = "Welcome to RevHire, " + employee.getFullName() + "!";
            String emailBody = MessageHandle.buildWelcomeEmployeeEmail(employee.getFullName());

            emailService.sendEmail(employee.getOfficialEmail(), subject, emailBody);

        } catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while saving the Employee", e);
        }
    }


    @Override
    public String getRoleByEmailAndPassword(String email, String password) throws DataServiceException {
        try {
            return employeeDAO.getRoleByEmailAndPassword(email, password);
        }catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while fetching the Role", e); // Wrap data layer exception
        }
    }


    @Override
    public Long getIdByEmailAndPassword(String email, String password) throws DataServiceException {
        try {
            return employeeDAO.getIdByEmailAndPassword(email, password);
        }catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while fetching the id", e); // Wrap data layer exception
        }

    }

    @Override
    public String getNameByEmailAndPassword(String email, String password) {
        return employeeDAO.getNameByEmailAndPassword(email, password);
    }

    @Override
    public Map<String, Object> login(String email, String password) throws BusinessServiceException {
        Map<String, Object> response = new HashMap<>();

        try {

            Optional<String> role = Optional.ofNullable(getRoleByEmailAndPassword(email, password));
            Optional<Long> id = Optional.ofNullable(getIdByEmailAndPassword(email, password));
            Optional<String> fullName = Optional.ofNullable(getNameByEmailAndPassword(email, password));

            if (role.isPresent() && id.isPresent()) {
                response.put("role", role.get());
                response.put("id", id.get());
                response.put("fullName", fullName.orElse("Unknown User"));
            } else {
            }
        } catch (DataServiceException e) {
            throw new BusinessServiceException("Error during login operation", e);
        }

        return response;
    }




    /**
     *
     * @param email
     * @param password
     * @return
     * @throws BusinessServiceException
     */
    @Override
    public boolean updateEmployeePassword(String email, String password) throws BusinessServiceException {
        try {
            return employeeDAO.updateEmployeePassword(email, password);
        } catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while fetching the id", e); // Wrap data layer exception

        }
    }

    @Override
    public boolean isEmailExists(String email) throws DataServiceException {
        try {
            return employeeDAO.isEmailExists(email);
        }
        catch(DataServiceException e) {
            throw new BusinessServiceException("Data layer error while checking the email", e); // Wrap data layer exception
        }
    }

    @Override
    public Employee getEmployeeeById(Long id) {
        try {
            return employeeDAO.getEmployeeeById(id);
        }
        catch(DataServiceException e) {
            throw new BusinessServiceException("Data layer error while fetching the EmployeeId", e);
        }
    }

    @Override
    public void saveJobPostings(JobPosting jobPosting) {
        try {
            employeeDAO.saveJobPostings(jobPosting);
        }
        catch(DataServiceException e) {
            throw new BusinessServiceException("Data layer error while Saving the JobPosting", e);
        }

    }

    @Override
    public Map<String, Object> getallJobPosting(int page, int size) throws BusinessServiceException {
        return employeeDAO.getallJobPosting(page,size);
    }



    @Override
    public Employee updateEmployee(Long id, Employee updatedEmployee) throws DataServiceException {
        try {
            return employeeDAO.updateEmployee(id, updatedEmployee);
        }
        catch(DataServiceException e) {
            throw new BusinessServiceException("Data layer error While Updating the Employee", e);
        }

    }

    @Override
    public List<JobPosting> getJobPostingsByJobSeekerId(Long id) throws DataServiceException{
        try {
            return employeeDAO.getJobPostingsByJobSeekerId(id);
        }
        catch(DataServiceException e) {
            throw new BusinessServiceException("Data layer error While fetching the job postings by jobseeker id", e);
        }

    }


    @Override
    public Long getApplicantCount(Long jobPostingId) throws DataServiceException {
        try {
            return employeeDAO.getApplicantCount(jobPostingId);
        } catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error While fetching the count of JobPostings", e);
        }
    }

    @Override
    public List<Resume> findResumesByJobPostingId(Long jobPostingId) throws DataServiceException {
        try {
            return employeeDAO.findResumesByJobPostingId(jobPostingId);
        } catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error While find by resume by job posting id", e);
        }
    }

    @Override
    public List<Applications> getApplicationById(Long id) throws DataServiceException {
        try {
            return employeeDAO.getApplicationById(id);
        }
        catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error While fetching the get application by id", e);
        }

    }

    @Override
    public Applications updatApplication(Long id, Applications applications) throws DataServiceException{
        try {
            return employeeDAO.updatApplication(id, applications);
        }
        catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error While Updating the application", e);
        }

    }

    @Override
    public Applications getStatusByApplicationId(Long applicationId) throws DataServiceException {
        try {
            return employeeDAO.getStatusByApplicationId(applicationId);
        }
        catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error While Updating the application", e);
        }

    }
    @Override
    public void shortlistApplication(Long jobPostingId, Long jobSeekerId, Long applicationId)
            throws BusinessServiceException, MessagingException {
        try {
            // Fetching the Job Seeker and Job Posting details
            JobSeeker jobSeeker = jobService.getJobSeekerById(jobSeekerId);
            JobPosting jobPosting = jobService.getJobPostingById(jobPostingId);

            // Retrieving application status if applicationId is provided
            Applications applications = getStatusByApplicationId(applicationId);
            String applicationStatus = applications.getStatus(); // Assuming getStatus() returns a String like "Shortlisted", "Rejected", or "Pending"

            // Construct the subject based on the application status
            String subject = switch (applicationStatus) {
                case "Shortlisted" -> "Congratulations! You've been shortlisted for " + jobPosting.getCompanyName();
                case "Rejected" -> "Application Update from " + jobPosting.getCompanyName();
                default -> "Application Status Update for " + jobPosting.getCompanyName();
            };

            String emailBody =MessageHandle.buildApplicationStatusEmail(jobSeeker.getFullName(), jobPosting.getCompanyName(), applicationStatus);

            emailService.sendEmail(jobSeeker.getEmail(), subject, emailBody);

        } catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while updating the application", e);
        }
    }


    @Override
    public void deleteJobPostingById(Long id) throws DataServiceException{
        try {
            employeeDAO.deleteJobPostingById(id);
        } catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error While Deleting the Job Posting", e);
        }


    }


}
