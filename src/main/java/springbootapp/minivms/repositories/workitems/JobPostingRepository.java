package springbootapp.minivms.repositories.workitems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springbootapp.minivms.model.entities.workitems.JobPosting;

import java.util.List;
import java.util.UUID;

public interface JobPostingRepository extends JpaRepository<JobPosting, UUID> {


    @Query("""
           select jp from JobPosting as jp where jp.buyer.uuid = :loggedUserUuid
""")
    List<JobPosting> getJobPostingForBuyer(UUID loggedUserUuid);
}
