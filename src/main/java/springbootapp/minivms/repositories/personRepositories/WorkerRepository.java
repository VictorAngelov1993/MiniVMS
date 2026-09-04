package springbootapp.minivms.repositories.personRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootapp.minivms.model.entities.persons.Worker;

import java.util.UUID;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, UUID> {

    int countAllByUsername(String username);
}
