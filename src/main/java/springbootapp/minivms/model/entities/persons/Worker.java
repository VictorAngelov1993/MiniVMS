package springbootapp.minivms.model.entities.persons;

import jakarta.persistence.Entity;

@Entity
public class Worker extends AbstractPerson{

    // TODO below should be WorkOrder not String Create it when the Work Order class is created
    private String workOrder;


}
