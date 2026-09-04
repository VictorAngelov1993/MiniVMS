package springbootapp.minivms.model.entities.persons;

import jakarta.persistence.Entity;

@Entity
public class Worker extends AbstractPerson{


    private String workOrderId;

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }
}
