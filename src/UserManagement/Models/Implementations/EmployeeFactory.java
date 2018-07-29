package UserManagement.Models.Implementations;

import UserManagement.Models.Abstracts.AEmployee;
import UserManagement.Models.Abstracts.IEmployeeFactory;

public class EmployeeFactory implements IEmployeeFactory {
    @Override
    public AEmployee getEmployee() {
        return new Employee();
    }
}
