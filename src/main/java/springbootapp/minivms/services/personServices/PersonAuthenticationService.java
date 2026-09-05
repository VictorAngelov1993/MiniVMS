package springbootapp.minivms.services.personServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springbootapp.minivms.model.entities.persons.AbstractPerson;
import springbootapp.minivms.repositories.personRepositories.BuyerRepository;
import springbootapp.minivms.repositories.personRepositories.SupplierRepository;
import springbootapp.minivms.repositories.personRepositories.WorkerRepository;

import java.util.Optional;

@Service
public class PersonAuthenticationService {
    private final BuyerRepository buyerRepository;
    private final SupplierRepository supplierRepository;
    private final WorkerRepository workerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonAuthenticationService(BuyerRepository buyerRepository,
                                       SupplierRepository supplierRepository,
                                       WorkerRepository workerRepository,
                                       PasswordEncoder passwordEncoder) {
        this.buyerRepository = buyerRepository;
        this.supplierRepository = supplierRepository;
        this.workerRepository = workerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AbstractPerson authenticate(String username, String rawPassword) {

        AbstractPerson person = this.findPersonByUsername(username)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid Username or Password"));

        if(!passwordEncoder.matches(rawPassword, person.getPassword())) {
            throw new IllegalArgumentException("Invalid Username or Password");
        }
        return person;
    }


    private Optional<AbstractPerson> findPersonByUsername(String username) {
        return this.buyerRepository.getBuyerByUsername(username).map(buyer -> (AbstractPerson) buyer)
                .or(() -> this.supplierRepository.getSupplierByUsername(username).map(supplier -> (AbstractPerson) supplier))
                .or(() -> this.workerRepository.getWorkerByUsername(username).map(worker -> (AbstractPerson) worker));
    }
}
