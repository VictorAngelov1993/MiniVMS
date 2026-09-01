package springbootapp.minivms.model.dto.personDto;

import springbootapp.minivms.model.entities.persons.enums.Role;

public class PersonRegistrationDto {
    private String username;
    private String fistName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    // The Work Order id is used only when the Role is Worker.
    private String workOrderId;
    public PersonRegistrationDto() {



    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getWorkOrder() {
        return workOrderId;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrderId = workOrder;
    }
}
