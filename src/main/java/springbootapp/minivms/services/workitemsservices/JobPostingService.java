package springbootapp.minivms.services.workitemsservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootapp.minivms.repositories.workitems.JobPostingRepository;

@Service
public class JobPostingService {

    private JobPostingRepository jobPostingRepository;

    @Autowired
    public JobPostingService(JobPostingRepository jobPostingRepository) {
        this.jobPostingRepository = jobPostingRepository;
    }

}
