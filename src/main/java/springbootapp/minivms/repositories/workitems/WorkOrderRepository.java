package springbootapp.minivms.repositories.workitems;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootapp.minivms.model.entities.persons.Buyer;
import springbootapp.minivms.model.entities.workitems.WorkOrder;

import java.util.List;
import java.util.UUID;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, UUID> {

    List<WorkOrder> getAllByJobPosting_BuyerUuid(UUID jobPostingBuyerUuid);

    int countByJobPosting_Buyer(Buyer jobPostingBuyer);
}
