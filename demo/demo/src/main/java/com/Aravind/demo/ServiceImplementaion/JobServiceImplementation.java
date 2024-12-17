package com.Aravind.demo.ServiceImplementaion;

import com.Aravind.demo.Dao.JobSeekerDao;
import com.Aravind.demo.Exception.BusinessServiceException;
import com.Aravind.demo.Exception.DataServiceException;
import com.Aravind.demo.Message.MessageHandle;
import com.Aravind.demo.Service.EmailService;
import com.Aravind.demo.Service.JobService;
import com.Aravind.demo.entity.Applications;
import com.Aravind.demo.entity.JobPosting;
import com.Aravind.demo.entity.JobSeeker;
import com.Aravind.demo.entity.Resume;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class JobServiceImplementation implements JobService {


    @Autowired
    private EmailService emailService;

    private final JobSeekerDao jobSeekerDao;
    @Autowired
    public JobServiceImplementation(JobSeekerDao jobSeekerDao) {
        this.jobSeekerDao = jobSeekerDao;
    }

    @Override
    public void registerJobSeeker(JobSeeker jobSeeker) throws BusinessServiceException, MessagingException {
        try {
            jobSeekerDao.saveJobseeker(jobSeeker);

            String subject = "Welcome to RevHire, " + jobSeeker.getFullName() + "!";

            String emailBody = MessageHandle.buildWelcomeEmail(jobSeeker.getFullName());

            emailService.sendEmail(jobSeeker.getEmail(), subject, emailBody);

        } catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while registering JobSeeker", e);
        }
    }



    @Override
    public String getRoleByEmailAndPassword(String email, String password) throws DataServiceException {
        try {
            return jobSeekerDao.getRoleByEmailAndPassword(email,password);
        }  catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while fetching the Role of JobSeeker", e); // Wrap data layer exception
        }
    }

    @Override
    public Long getIdByEmailAndPassword(String email, String password) throws DataServiceException{
        try {
            return jobSeekerDao.getIdByEmailAndPassword(email, password);
        }  catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while fetching the Id of JobSeeker", e);
        }

    }

    @Override
    public String getNameByEmailAndPassword(String email, String password) throws DataServiceException {
        try {
            return jobSeekerDao.getNameByEmailAndPassword(email, password);
        }catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while fetching the Name of JobSeeker", e);
        }

    }

    @Override
    public void saveJobResume(Resume resume) throws DataServiceException {
        try {
            jobSeekerDao.saveJobResume(resume);
        }catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while Saving the Resume", e);
        }

    }

    @Override
    public boolean checkResumeExistence(Long jobseekerid) throws DataServiceException {
        try {
            return jobSeekerDao.checkResumeExistence(jobseekerid);
        } catch (DataServiceException e) {
            throw new BusinessServiceException("already exits",e);
        }
    }

    @Override
    public JobSeeker getJobSeekerById(Long id) throws DataServiceException {
        try {
            return jobSeekerDao.getJobSeekerId(id);
        }catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while fetching the JobSeekerId", e);
        }

    }

    @Override
    public Resume getResumeByJobseekerId(Long id) {
        return jobSeekerDao.getResumeByJobseekerId(id);
    }

    @Override
    public boolean updateJobSeekerPassword(String email, String password) throws DataServiceException {
        try {
            return jobSeekerDao.updateJobSeekerPassword(email, password);
        }catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while updating the password", e);
        }

    }

    @Override
    public boolean isEmailExists(String email) throws DataServiceException{
        try {
            return jobSeekerDao.isEmailExists(email);
        }
        catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while checking the Email", e);
        }
    }

    @Override
    public void registerApplication(Long jobSeekerId, Long jobPostingId, Long resumeId, Applications application)
            throws BusinessServiceException, MessagingException {

        try {

            JobSeeker jobSeeker = getJobSeekerById(jobSeekerId);
            JobPosting jobPosting = getJobPostingById(jobPostingId);
            Resume resume = getResumeById(resumeId);


            application.setJobSeeker(jobSeeker);
            application.setJobPosting(jobPosting);
            application.setResume(resume);

            jobSeekerDao.submitApplication(application);

            String subject = "Application Received for " + jobPosting.getCompanyName();

            String emailBody = MessageHandle.buildApplicationReceivedEmail(jobSeeker.getFullName(), jobPosting.getCompanyName());

            emailService.sendEmail(jobSeeker.getEmail(), subject, emailBody);

        } catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while submitting the application", e);
        }
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




    @Override
    public boolean hasUserApplied(Long jobId, Long userId) throws DataServiceException {
        try {
            return jobSeekerDao.hasUserApplied(jobId, userId);
        } catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while checking the jobposting id ", e);
        }
    }

    @Override
    public JobPosting getJobPostingById(Long jobpostingId) throws DataServiceException{
        try {
            return jobSeekerDao.getJobPostingById(jobpostingId);
        }
        catch (DataServiceException e)
        {
            throw new BusinessServiceException("Data layer error fetching the jobpostingId", e);

        }
    }

    @Override
    public Resume getResumeById(Long resumeid) throws DataServiceException{
        try {
            return jobSeekerDao.getResumeById(resumeid);
        }
        catch (DataServiceException e)
        {
            throw new BusinessServiceException("Data layer error fetching the ResumeId", e);

        }

    }

    @Override
    public JobSeeker updateJobSeeker(Long id, JobSeeker updatedJobSeeker) throws DataServiceException {
        try {
            return jobSeekerDao.updateJobSeeker(id, updatedJobSeeker);
        }  catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while updating JobSeeker", e); // Wrap data layer exception
        }
    }

    @Override
    public List<Applications> getApplicationsByJobSeeker(Long id) throws DataServiceException {
        try {
            return jobSeekerDao.getApplicationsByJobSeeker(id);
        }
        catch (DataServiceException e) {
            throw new BusinessServiceException("Data layer error while fetching the getapplicationbyJobseekerid", e); // Wrap data layer exception
        }
    }

    @Override
    public boolean withdrawApplication(Long jobSeekerId, Long applicationId) throws DataServiceException{
        try {
            return jobSeekerDao.withdrawApplication(jobSeekerId, applicationId);
        }
        catch(DataServiceException e) {
            throw new BusinessServiceException("Data layer error while with Draw application", e); // Wrap data layer exception
        }
    }

    @Override
    public List<JobPosting> searchJobs(String jobTitle, String location, String experience) throws DataServiceException {
        try {
            return jobSeekerDao.searchJobs(jobTitle, location, experience);
        }
        catch(DataServiceException e) {
            throw new BusinessServiceException("Data layer error while Searching", e); // Wrap data layer exception
        }
    }

    @Override
    public Resume updatedResume(Resume resume, Long id) throws DataServiceException {
        try {
            return jobSeekerDao.updatedResume(resume, id);
        }
        catch(DataServiceException e) {
            throw new BusinessServiceException("Data layer error while Updating the resume", e); // Wrap data layer exception
        }
    }


}

