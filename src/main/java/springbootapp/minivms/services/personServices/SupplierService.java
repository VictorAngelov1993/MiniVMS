package springbootapp.minivms.services.personServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootapp.minivms.model.entities.persons.Supplier;
import springbootapp.minivms.repositories.personRepositories.BuyerRepository;
import springbootapp.minivms.repositories.personRepositories.SupplierRepository;
import springbootapp.minivms.repositories.personRepositories.WorkerRepository;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final BuyerRepository buyerRepository;
    private final WorkerRepository workerRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository,
                           BuyerRepository buyerRepository,
                           WorkerRepository workerRepository) {
        this.supplierRepository = supplierRepository;
        this.buyerRepository = buyerRepository;
        this.workerRepository = workerRepository;
    }

    public void register(Supplier supplier) {
        //Below checks if a different user role has the same Username
        if(this.supplierRepository.countAllByUsername(supplier.getUsername()) == 1
        || this.buyerRepository.countAllByUsername(supplier.getUsername()) == 1
        || this.workerRepository.countAllByUsername(supplier.getUsername()) == 1) {
            throw new IllegalArgumentException("Username already exist");
        }
        this.supplierRepository.save(supplier);
    }
}
