package Login.Models.Abstracts;

import Login.Models.Implementations.Employee;

public interface IEmployeeCreate {
     void addNewEmployee(Employee employee) throws Exception;
}
