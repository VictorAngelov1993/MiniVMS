package springbootapp.minivms.services.workitemsservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootapp.minivms.mappers.JobSeekerMapper;
import springbootapp.minivms.model.dto.workitemdto.JobSeekerCreateDto;
import springbootapp.minivms.model.dto.workitemdto.JobSeekerSubmitDto;
import springbootapp.minivms.model.entities.persons.Supplier;
import springbootapp.minivms.model.entities.workitems.JobSeeker;
import springbootapp.minivms.repositories.personrepositories.SupplierRepository;
import springbootapp.minivms.repositories.workitems.JobSeekerRepository;

import java.util.List;
import java.util.UUID;

@Service
public class JobSeekerService {

    private JobSeekerRepository jobSeekerRepository;
    private SupplierRepository supplierRepository;
    private JobSeekerMapper jobSeekerMapper;

    @Autowired
    public JobSeekerService(JobSeekerRepository jobSeekerRepository,
                            SupplierRepository supplierRepository,
                            JobSeekerMapper jobSeekerMapper) {
        this.jobSeekerRepository = jobSeekerRepository;
        this.supplierRepository = supplierRepository;
        this.jobSeekerMapper = jobSeekerMapper;
    }

    public List<JobSeeker> getAllJobSeekersForTheSupplier(UUID supplierUuid) {
        // I will get the Supplier user here instead of the controller
        Supplier loggedSupplier = this.supplierRepository.getSupplierByUuid(supplierUuid);
        return this.jobSeekerRepository.getJobSeekerBySupplier(loggedSupplier);
    }

    public void createJobSeeker(JobSeekerCreateDto jobSeekerCreateDto, UUID supplierUuid) {
        // Get the supplier who is logged in so to map it to the Job Seeker so that we know who created the Job Seeker
        Supplier supplier = this.supplierRepository.getSupplierByUuid(supplierUuid);
        JobSeeker jobSeeker = this.jobSeekerMapper.mapJobSeekerCreateDtoToJobSeekerEntity(jobSeekerCreateDto, supplier);

        // generate the jobseeker id and add it.
        String jobSeekerId = this.generateJobSeekerId();
        jobSeeker.setJobSeekerId(jobSeekerId);

        this.jobSeekerRepository.save(jobSeeker);
    }

    public List<JobSeekerSubmitDto> getAllJobSeekersSuitableForSubmission() {
        List<JobSeeker> jobSeekers = this.jobSeekerRepository.findAll();
        return this.jobSeekerMapper.mapJobSeekersToJobSeekerSubmitDto(jobSeekers);
    }

    private String generateJobSeekerId() {
        long coundJobSeekers = this.jobSeekerRepository.count();
        return String.format("JS-%03d", coundJobSeekers + 1);
    }

    public JobSeekerSubmitDto getJobSeekerSubmitDtoById(String jobSeekerId) {

        JobSeeker jobSeeker = this.jobSeekerRepository.getJobSeekerByJobSeekerId(jobSeekerId);
        return this.jobSeekerMapper.mapJobSeekerToJobSeekerDto(jobSeeker);
    }

    public JobSeeker getJobSeekerById(String jobSeekerId) {
        return this.jobSeekerRepository.getJobSeekerByJobSeekerId(jobSeekerId);
    }
}
