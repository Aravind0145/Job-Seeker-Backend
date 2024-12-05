package com.Aravind.demo.ServiceImplementaion;

import com.Aravind.demo.Dao.EmployeeDao;
import com.Aravind.demo.Exception.BusinessServiceException;
import com.Aravind.demo.Exception.DataServiceException;
import com.Aravind.demo.Service.EmployeeService;
import com.Aravind.demo.entity.Applications;
import com.Aravind.demo.entity.Employee;
import com.Aravind.demo.entity.JobPosting;
import com.Aravind.demo.entity.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    private final EmployeeDao employeeDAO;

    @Autowired
    public EmployeeServiceImplementation(EmployeeDao employeeDAO) {
        this.employeeDAO = employeeDAO;
    }


    @Override
    public void saveEmployees(Employee employee) throws DataServiceException {
        try {
            employeeDAO.saveEmployees(employee);
        }catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while Saving the Employee", e); // Wrap data layer exception
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

   /* @Override
    public List<JobPosting> getallJobPosting() {
        try {
            return employeeDAO.getallJobPosting();
        } catch(DataServiceException e) {
            throw new BusinessServiceException("Data layer error while fetching the All the applications", e);
        }

    }*/

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
    public void deleteJobPostingById(Long id) throws DataServiceException{
        try {
            employeeDAO.deleteJobPostingById(id);
        } catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error While Deleting the Job Posting", e);
        }


    }


}
