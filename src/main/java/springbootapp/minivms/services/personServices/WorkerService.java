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
        if(this.workerRepository.countAllByUsername(worker.getUsername()) == 1) {
            throw new IllegalArgumentException("Username already exist");
        }
        // below is preventive exception because the Work Order is not yet implemented.
        throw new IllegalArgumentException("Worker Registration is pending implementation");
        // TODO Add the Work Order validator HERE
        //this.workerRepository.save(worker);
    }
}
