package springbootapp.minivms.services.personServices;

import org.springframework.stereotype.Service;
import springbootapp.minivms.model.entities.persons.Worker;
import springbootapp.minivms.repositories.personRepositories.WorkerRepository;

@Service
public class WorkerService {

    private WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public void register(Worker worker) {

    }
}
