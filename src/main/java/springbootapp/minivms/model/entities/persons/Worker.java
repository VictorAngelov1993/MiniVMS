package springbootapp.minivms.model.entities.persons;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import springbootapp.minivms.model.entities.workitems.WorkOrder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Worker extends AbstractPerson{


    private String workOrderId;

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    @OneToMany(mappedBy = "worker")
    private List<WorkOrder> workOrders = new ArrayList<>();

    public List<WorkOrder> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(List<WorkOrder> workOrders) {
        this.workOrders = workOrders;
    }
}
