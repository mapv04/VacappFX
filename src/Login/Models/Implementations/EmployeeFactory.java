package Login.Models.Implementations;

import Login.Models.Abstracts.AEmployee;
import Login.Models.Abstracts.IEmployeeFactory;

public class EmployeeFactory implements IEmployeeFactory {
    @Override
    public AEmployee getEmployee() {
        return new Employee();
    }
}
