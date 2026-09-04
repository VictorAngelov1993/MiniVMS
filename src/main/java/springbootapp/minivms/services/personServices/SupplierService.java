package springbootapp.minivms.services.personServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootapp.minivms.model.entities.persons.Supplier;
import springbootapp.minivms.repositories.personRepositories.SupplierRepository;

@Service
public class SupplierService {

    private SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public void register(Supplier supplier) {
        if(this.supplierRepository.countAllByUsername(supplier.getUsername()) == 1) {
            throw new IllegalArgumentException("Username already exist");
        }
        this.supplierRepository.save(supplier);
    }
}
