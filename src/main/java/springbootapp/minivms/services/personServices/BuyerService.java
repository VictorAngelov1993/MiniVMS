package springbootapp.minivms.services.personServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootapp.minivms.model.entities.persons.Buyer;
import springbootapp.minivms.repositories.personRepositories.BuyerRepository;
import springbootapp.minivms.repositories.personRepositories.SupplierRepository;
import springbootapp.minivms.repositories.personRepositories.WorkerRepository;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepository;
    private final SupplierRepository supplierRepository;
    private final WorkerRepository workerRepository;

    @Autowired
    public BuyerService(BuyerRepository buyerRepository,
                        SupplierRepository supplierRepository,
                        WorkerRepository workerRepository) {
        this.buyerRepository = buyerRepository;
        this.supplierRepository = supplierRepository;
        this.workerRepository = workerRepository;
    }

    public void register(Buyer buyer) {
        if(this.buyerRepository.countAllByUsername(buyer.getUsername()) == 1
        || this.supplierRepository.countAllByUsername(buyer.getUsername()) == 1
        || this.workerRepository.countAllByUsername(buyer.getUsername()) == 1) {
            throw new IllegalArgumentException("Username already exist");
        }
        this.buyerRepository.save(buyer);

    }
}
