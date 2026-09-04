package springbootapp.minivms.services.personServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootapp.minivms.model.entities.persons.Buyer;
import springbootapp.minivms.repositories.personRepositories.BuyerRepository;

@Service
public class BuyerService {

    private BuyerRepository buyerRepository;

    @Autowired
    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public void register(Buyer buyer) {
        if(this.buyerRepository.countAllByUsername(buyer.getUsername()) == 1) {
            throw new IllegalArgumentException("Username already exist");
        }
        this.buyerRepository.save(buyer);

    }
}
