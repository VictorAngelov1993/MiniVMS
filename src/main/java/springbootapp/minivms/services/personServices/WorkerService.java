package springbootapp.minivms.services.personServices;

import org.springframework.stereotype.Service;
import springbootapp.minivms.model.entities.persons.Worker;
import springbootapp.minivms.repositories.personRepositories.BuyerRepository;
import springbootapp.minivms.repositories.personRepositories.SupplierRepository;
import springbootapp.minivms.repositories.personRepositories.WorkerRepository;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final BuyerRepository buyerRepository;
    private final SupplierRepository supplierRepository;

    public WorkerService(WorkerRepository workerRepository,
                         BuyerRepository buyerRepository,
                         SupplierRepository supplierRepository) {
        this.workerRepository = workerRepository;
        this.buyerRepository = buyerRepository;
        this.supplierRepository = supplierRepository;
    }

    public void register(Worker worker) {
        if(this.workerRepository.countAllByUsername(worker.getUsername()) == 1
        || this.buyerRepository.countAllByUsername(worker.getUsername()) == 1
        || this.supplierRepository.countAllByUsername(worker.getUsername()) == 1) {
            throw new IllegalArgumentException("Username already exist");
        }
        // below is preventive exception because the Work Order is not yet implemented.
        throw new IllegalArgumentException("Worker Registration is pending implementation");
        // TODO Add the Work Order validator HERE
        //this.workerRepository.save(worker);
    }
}
