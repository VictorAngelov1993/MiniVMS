package springbootapp.minivms.mappers;

import org.springframework.stereotype.Component;
import springbootapp.minivms.model.dto.workitemdto.WorkOrderCardDto;
import springbootapp.minivms.model.entities.workitems.WorkOrder;

import java.util.List;

@Component
public class WorkOrderMapper {

    public List<WorkOrderCardDto> mapListOfWorkOrderToListCardDto(List<WorkOrder> workOrders) {
        return workOrders.stream().map(this::mapWorkOrderToWorkOrderCardDto).toList();
    }

    public WorkOrderCardDto mapWorkOrderToWorkOrderCardDto(WorkOrder workOrder) {
        WorkOrderCardDto workOrderCardDto = new WorkOrderCardDto();
        workOrderCardDto.setId(workOrder.getWorkOrderId());
        workOrderCardDto.setStatus(workOrder.getStatus());
        workOrderCardDto.setAssignedWorkerName(
                workOrder.getJobSeeker().getFirstName()
                + " " +
                workOrder.getJobSeeker().getLastName());
        workOrderCardDto.setStartDate(workOrder.getStartDate());
        workOrderCardDto.setEndDate(workOrder.getEndDate());
        return workOrderCardDto;
    }

}
