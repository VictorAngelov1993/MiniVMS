package springbootapp.minivms.services.workitemsservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootapp.minivms.mappers.WorkOrderMapper;
import springbootapp.minivms.model.dto.workitemdto.WorkOrderCardDto;
import springbootapp.minivms.model.entities.workitems.WorkOrder;
import springbootapp.minivms.repositories.workitems.WorkOrderRepository;

import java.util.List;
import java.util.UUID;

@Service
public class WorkOrderService {
    private WorkOrderRepository workOrderRepository;
    private WorkOrderMapper workOrderMapper;

    @Autowired
    public WorkOrderService(WorkOrderRepository workOrderRepository, WorkOrderMapper workOrderMapper) {
        this.workOrderRepository = workOrderRepository;
        this.workOrderMapper = workOrderMapper;
    }

    public String autoGenerateWorkOrderId() {
        // Below will cause issues if a Work Order is deleted from the DB.
        long countWorkOrders = this.workOrderRepository.count();
        return String.format("WO-%03d", countWorkOrders + 1);
    }

    public List<WorkOrderCardDto> getAllWorkOrdersAsCards(UUID loggedUserUUid) {
        List<WorkOrder> allWorkOrders = this.workOrderRepository.getAllByJobPosting_BuyerUuid(loggedUserUUid);
        return this.workOrderMapper.mapListOfWorkOrderToListCardDto(allWorkOrders);
    }
}
