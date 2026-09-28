package springbootapp.minivms.services.workitemsservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootapp.minivms.repositories.workitems.WorkOrderRepository;

@Service
public class WorkOrderService {
    private WorkOrderRepository workOrderRepository;

    @Autowired
    public WorkOrderService(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }

    public String autoGenerateWorkOrderId() {
        // Below will cause issues if a Work Order is deleted from the DB.
        long countWorkOrders = this.workOrderRepository.count();
        return String.format("WO-%03d", countWorkOrders + 1);
    }
}
