package springbootapp.minivms.repositories.workitems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootapp.minivms.model.entities.workitems.JobSeekerSubmission;

import java.util.UUID;

@Repository
public interface JobSeekerSubmissionRepository extends JpaRepository<JobSeekerSubmission, UUID> {
}
