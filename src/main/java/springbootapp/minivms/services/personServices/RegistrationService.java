package springbootapp.minivms.services.personServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springbootapp.minivms.mappers.MapPersonRegistrationDtoToEntity;
import springbootapp.minivms.model.dto.personDto.PersonRegistrationDto;
import springbootapp.minivms.model.entities.persons.AbstractPerson;
import springbootapp.minivms.model.entities.persons.Buyer;
import springbootapp.minivms.model.entities.persons.Supplier;
import springbootapp.minivms.model.entities.persons.Worker;

@Service
public class RegistrationService {
    private final BuyerService buyerService;
    private final SupplierService supplierService;
    private final WorkerService workerService;
    private final MapPersonRegistrationDtoToEntity mapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(BuyerService buyerService,
                               SupplierService supplierService,
                               WorkerService workerService,
                               MapPersonRegistrationDtoToEntity mapper,
                               PasswordEncoder passwordEncoder) {
        this.buyerService = buyerService;
        this.supplierService = supplierService;
        this.workerService = workerService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;

    }

    public void registerUser(PersonRegistrationDto personRegistrationDto) {

        this.validatePersonRegistrationDto(personRegistrationDto);

        // no need for default because the value is coming from a dropdown and will always be one of the tree.
        switch (personRegistrationDto.getRole()) {
            case BUYER -> {
                Buyer buyer = this.mapper.registrationDtoToBuyer(personRegistrationDto);
                this.encodePassword(buyer);
                this.buyerService.register(buyer);
            }
            case SUPPLIER ->{
                Supplier supplier = this.mapper.registrationDtoToSupplier(personRegistrationDto);
                this.encodePassword(supplier);
                this.supplierService.register(supplier);
            }
            case WORKER -> {
                Worker worker = this.mapper.registrationDtoToWorker(personRegistrationDto);
                this.encodePassword(worker);
                this.workerService.register(worker);
            }
        }
    }

    private void encodePassword(AbstractPerson person) {
        String encoded = passwordEncoder.encode(person.getPassword());
        person.setPassword(encoded);
    }

    private void validatePersonRegistrationDto(PersonRegistrationDto personRegistrationDto) {
        String username = personRegistrationDto.getUsername();
        String firstName = personRegistrationDto.getFirstName();
        String lastName = personRegistrationDto.getLastName();
        String email = personRegistrationDto.getEmail();
        String password = personRegistrationDto.getPassword();

        if(username.length() < 3) {
            throw new IllegalArgumentException("Username should be at least 3 characters");
        }
        if(firstName.isEmpty() || lastName.isEmpty()) {
            throw new IllegalArgumentException("Name should be at least 1 character");
        }
        String regex = "^[A-Za-z0-9]+([._-][A-Za-z0-9]+)*@[A-Za-z]+(-[A-Za-z]+)*(\\.[A-Za-z]+(-[A-Za-z]+)*)+$";
        boolean isEmailValid = email.matches(regex);

        if(!isEmailValid) {
            throw new IllegalArgumentException("Email format should match global standards example@example.com");
        }
        if(password.length() < 5) {
            throw new IllegalArgumentException("Password should be at least 5 characters");
        }
    }


}
