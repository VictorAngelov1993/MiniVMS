package springbootapp.minivms.repositories.workitems;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootapp.minivms.model.entities.workitems.JobPosting;

import java.util.UUID;

public interface JobPostingRepository extends JpaRepository<JobPosting, UUID> {
}
