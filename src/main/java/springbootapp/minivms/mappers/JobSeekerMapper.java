package springbootapp.minivms.mappers;

import org.springframework.stereotype.Component;
import springbootapp.minivms.model.dto.workitemdto.JobSeekerCreateDto;
import springbootapp.minivms.model.entities.persons.Supplier;
import springbootapp.minivms.model.entities.workitems.JobSeeker;


@Component
public class JobSeekerMapper {


    public JobSeeker mapJobSeekerCreateDtoToJobSeekerEntity(JobSeekerCreateDto jobSeekerCreateDto, Supplier supplier) {
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setSupplier(supplier);
        jobSeeker.setFirstName(jobSeekerCreateDto.getFirstName());
        jobSeeker.setLastName(jobSeekerCreateDto.getLastName());
        jobSeeker.setDescription(jobSeekerCreateDto.getDescription());
        jobSeeker.setEmail(jobSeekerCreateDto.getEmail());
        jobSeeker.setPhoneNumber(jobSeekerCreateDto.getPhoneNumber());
        return jobSeeker;
    }

}
