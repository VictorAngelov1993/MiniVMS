package springbootapp.minivms.services.workitemsservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootapp.minivms.model.entities.workitems.JobPosting;
import springbootapp.minivms.repositories.workitems.JobPostingRepository;

import java.util.List;
import java.util.UUID;

@Service
public class JobPostingService {

    private JobPostingRepository jobPostingRepository;

    @Autowired
    public JobPostingService(JobPostingRepository jobPostingRepository) {
        this.jobPostingRepository = jobPostingRepository;
    }

    public List<JobPosting> getJobPostingForBuyer(UUID loggedUserUuid) {
        return this.jobPostingRepository.getJobPostingForBuyer(loggedUserUuid);
    }

    public List<JobPosting> getJobPostingForSupplier() {
        return this.jobPostingRepository.getJobPostingForSupplier();
    }

}
