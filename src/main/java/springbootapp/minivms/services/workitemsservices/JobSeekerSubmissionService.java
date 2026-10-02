package springbootapp.minivms.services.workitemsservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootapp.minivms.model.dto.workitemdto.JobPostingDetailsDto;
import springbootapp.minivms.model.dto.workitemdto.JobSeekerSubmitDto;
import springbootapp.minivms.model.entities.workitems.JobPosting;
import springbootapp.minivms.model.entities.workitems.JobSeeker;
import springbootapp.minivms.model.entities.workitems.JobSeekerSubmission;
import springbootapp.minivms.repositories.workitems.JobSeekerSubmissionRepository;

@Service
public class JobSeekerSubmissionService {

    private JobSeekerSubmissionRepository jobSeekerSubmissionRepository;
    private JobSeekerService jobSeekerService;
    private JobPostingService jobPostingService;

    @Autowired()
    public JobSeekerSubmissionService(JobSeekerSubmissionRepository jobSeekerSubmissionRepository,
                                      JobSeekerService jobSeekerService,
                                      JobPostingService jobPostingService) {
        this.jobSeekerSubmissionRepository = jobSeekerSubmissionRepository;
        this.jobSeekerService = jobSeekerService;
        this.jobPostingService = jobPostingService;
    }

    public void createJobSeekerSubmit(JobSeekerSubmitDto jobSeekerSubmitDto, JobPostingDetailsDto jobPostingDetailsDto) {
        JobSeeker jobSeeker = this.jobSeekerService.getJobSeekerById(jobSeekerSubmitDto.getJobSeekerId());
        JobPosting jobPosting = this.jobPostingService.getJobPostingById(jobPostingDetailsDto.getJobPostingId());
        JobSeekerSubmission jobSeekerSubmission = new JobSeekerSubmission();
        jobSeekerSubmission.setJobPosting(jobPosting);
        jobSeekerSubmission.setJobSeeker(jobSeeker);

        this.jobSeekerSubmissionRepository.save(jobSeekerSubmission);
    }
}
