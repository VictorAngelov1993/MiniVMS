package springbootapp.minivms.services.personservices;

import org.springframework.stereotype.Service;
import springbootapp.minivms.model.entities.persons.Worker;
import springbootapp.minivms.repositories.personrepositories.BuyerRepository;
import springbootapp.minivms.repositories.personrepositories.SupplierRepository;
import springbootapp.minivms.repositories.personrepositories.WorkerRepository;

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
        //Below checks if a different user role has the same Username
        if(this.workerRepository.countAllByUsername(worker.getUsername()) == 1
        || this.buyerRepository.countAllByUsername(worker.getUsername()) == 1
        || this.supplierRepository.countAllByUsername(worker.getUsername()) == 1) {
            throw new IllegalArgumentException("Username already exist");
        }
        // The Work Order validation is happening in the Work Order Service. The Worker here will have valid Work Order.
        worker.setWorkerId(this.generateWorkerID());
        this.workerRepository.save(worker);

    }

    private String generateWorkerID() {
        // This will fail if a worker is deleted from the database, but users should not be able to delete entities.
        return String.format("WK-%03d", workerRepository.count() + 1);
    }
}
